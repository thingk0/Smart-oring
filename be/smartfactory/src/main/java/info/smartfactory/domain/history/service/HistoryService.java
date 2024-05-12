package info.smartfactory.domain.history.service;


import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import info.smartfactory.domain.history.service.Mapper.AmrHistoryMapper;
import info.smartfactory.domain.history.service.Mapper.CurrentAmrMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final RedisTemplate<String, Object> liveRedisTemplate;
    private final RedisTemplate<String, Object> batchRedisTemplate;
    private final CurrentAmrRedisRepository currentAmrRedisRepository;
    private final BatchAmrRedisRepository batchAmrRedisRepository;
    private final BottleneckService bottleneckService;

    public HistoryService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
                          @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
                          CurrentAmrRedisRepository currentAmrRedisRepository,
                          BatchAmrRedisRepository batchAmrRedisRepository,
                          BottleneckService bottleneckService) {
        this.liveRedisTemplate = redisTemplate;
        this.batchRedisTemplate = batchRedisTemplate;
        this.currentAmrRedisRepository = currentAmrRedisRepository;
        this.batchAmrRedisRepository = batchAmrRedisRepository;
        this.bottleneckService = bottleneckService;
    }

    public void saveHistory(AmrHistoryLog amrHistoryLog) {
        // 병목 기간 저장

        Optional<CurrentAmrInfoRedisDto> previous = currentAmrRedisRepository.findById(amrHistoryLog.amrId().toString());

        long period = 0L;

        if (previous.isPresent()) {
            CurrentAmrInfoRedisDto previousAmrInfo = previous.get();

            if (!(amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) && previousAmrInfo.getStopPeriod() > 0L) {
                bottleneckService.addBottleneckData(BottleneckDto.builder()
                                                                 .missionId(amrHistoryLog.missionId())
                                                                 .amrId(amrHistoryLog.amrId())
                                                                 .xCoordinate(amrHistoryLog.xCoordinate())
                                                                 .yCoordinate(amrHistoryLog.yCoordinate())
                                                                 .bottleneckPeriod(previousAmrInfo.getStopPeriod())
                                                                 .bottleneckCreatedAt(amrHistoryLog.amrHistoryCreatedAt())
                                                                 .build());
            }

            if (amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) {
                period = previousAmrInfo.getStopPeriod() + 1L;
            }
        } else {
            if (amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) {
                period += 1L;
            }
        }

        // redis에 amr 실시간 위치 저장

        CurrentAmrInfoRedisDto redisDto = CurrentAmrMapper.INSTANCE.mapToRedisDto(amrHistoryLog);

        CurrentAmrMapper.INSTANCE.setStopPeriod(amrHistoryLog, redisDto, period);

        currentAmrRedisRepository.save(redisDto);

        // redis에 amr 이력 저장

        BatchAmrInfoRedisDto amrHistoryDto = AmrHistoryMapper.INSTANCE.mapToRedisDto(amrHistoryLog);

        AmrHistoryMapper.INSTANCE.setStopPeriod(amrHistoryLog, amrHistoryDto, period);

        batchAmrRedisRepository.save(amrHistoryDto);
    }

    // amr 현재 위치 가져오기

    public List<CurrentAmrInfoRedisDto> getRecentRobotStates() {
        List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();
        all.forEach(System.out::println);
        return all;
    }

    // amr 이력 정보 가져오기

    public List<BatchAmrInfoRedisDto> getRobotHistories() {
        List<BatchAmrInfoRedisDto> all = batchAmrRedisRepository.findAll();
        all.forEach(System.out::println);
        return all;
    }
}
