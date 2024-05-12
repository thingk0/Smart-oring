package info.smartfactory.domain.dashboard.service;

import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.bottleneck.service.ErrorDto;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final RedisTemplate<String, Object> liveRedisTemplate;
    private final RedisTemplate<String, Object> batchRedisTemplate;
    private final CurrentAmrRedisRepository currentAmrRedisRepository;
    private final BatchAmrRedisRepository batchAmrRedisRepository;

    @Autowired
    public DashboardService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
                          @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
                          CurrentAmrRedisRepository currentAmrRedisRepository,
                          BatchAmrRedisRepository batchAmrRedisRepository
                          ) {
        this.liveRedisTemplate = redisTemplate;
        this.batchRedisTemplate = batchRedisTemplate;
        this.currentAmrRedisRepository = currentAmrRedisRepository;
        this.batchAmrRedisRepository = batchAmrRedisRepository;
    }

    public DashboardDto getRealtimeData(){
        List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();

        // 생산량 - 오늘 실시간, 어제 데이터


        // 오늘 생산량

        // 가동룰 - 전체 Amr 중에 실시간 PROCESSING, BOTTLENECK인 것의 비율
        int processingNum = 0;

        // amr별 사용률

        // amr별 에러율


        // 실시간 에러, 병목

        List<ErrorDto> realtimeErrorList = new ArrayList<>();
        List<BottleneckDto> realtimeBottleneckList = new ArrayList<>();

        for(CurrentAmrInfoRedisDto amrInfo: all){
            if(amrInfo.getAmrStatus() == AmrStatus.BOTTLENECK){
                processingNum++;
                realtimeBottleneckList.add(BottleneckDto.builder()
                        .missionId(amrInfo.getMissionId())
                        .amrId(amrInfo.getAmrId())
                        .xCoordinate(amrInfo.getXCoordinate())
                        .yCoordinate(amrInfo.getYCoordinate())
                        .bottleneckPeriod(amrInfo.getStopPeriod())
                        .bottleneckCreatedAt(amrInfo.getAmrHistoryCreatedAt())
                        .build());
            }else if(amrInfo.getAmrStatus() == AmrStatus.ERROR){
                realtimeErrorList.add(ErrorDto.builder()
                        .missionId(amrInfo.getMissionId())
                        .amrId(amrInfo.getAmrId())
                        .xCoordinate(amrInfo.getXCoordinate())
                        .yCoordinate(amrInfo.getYCoordinate())
                        .errorCreatedAt(amrInfo.getAmrHistoryCreatedAt())
                        .build());
            }else if(amrInfo.getAmrStatus() == AmrStatus.PROCESSING){
                processingNum++;
            }
        }

        int totalUsagePercent = 0;

        if(processingNum > 0) totalUsagePercent = ((processingNum/50)*100);

        DashboardDto dashboardDto = DashboardDto.builder()
                .totalUsagePercent(totalUsagePercent)
                .realtimeBottleneck(realtimeBottleneckList)
                .realtimeError(realtimeErrorList)
                .build();

        return dashboardDto;
    }
}
