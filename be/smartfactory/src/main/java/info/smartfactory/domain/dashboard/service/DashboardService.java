package info.smartfactory.domain.dashboard.service;

import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.bottleneck.service.ErrorDto;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import info.smartfactory.domain.mission.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final RedisTemplate<String, Object> liveRedisTemplate;
    private final RedisTemplate<String, Object> batchRedisTemplate;
    private final CurrentAmrRedisRepository currentAmrRedisRepository;
    private final BatchAmrRedisRepository batchAmrRedisRepository;
    private final MissionRepository missionRepository;

    @Autowired
    public DashboardService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
                          @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
                          CurrentAmrRedisRepository currentAmrRedisRepository,
                          BatchAmrRedisRepository batchAmrRedisRepository,
                            MissionRepository missionRepository
                          ) {
        this.liveRedisTemplate = redisTemplate;
        this.batchRedisTemplate = batchRedisTemplate;
        this.currentAmrRedisRepository = currentAmrRedisRepository;
        this.batchAmrRedisRepository = batchAmrRedisRepository;
        this.missionRepository = missionRepository;
    }

    public DashboardDto getRealtimeData(){
        List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();

        // 생산량 - 오늘 실시간, 어제 데이터
        //어제와 오늘 데이터 중 미션 Type이 CONVEYER_TO_DESTINATION인 데이터
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Mission> missionList = missionRepository.getCompleteMissions(yesterday, today);

        // 어제 완성품 옮긴 미션 리스트
        List<Mission> yesterdayConveyerMissions = missionList.stream()
                .filter(mission -> mission.getMissionStartedAt().toLocalDate().equals(yesterday))
                .filter(mission -> mission.getMissionType() == MissionType.CONVEYER_TO_DESTINATION)
                .collect(Collectors.toList());

        // 오늘 수행한 전체 미션 리스트
        List<Mission> todayMissions = missionList.stream()
                .filter(mission -> mission.getMissionStartedAt().toLocalDate().equals(today))
                .collect(Collectors.toList());

        // 오늘 수행한 미션 중 완성품 옮긴 미션 리스트
        List<Mission> todayConveyerMissions = todayMissions.stream()
                .filter(mission -> mission.getMissionType() == MissionType.CONVEYER_TO_DESTINATION)
                .collect(Collectors.toList());


        // 오늘 생산량
        long todayTotalOutput = todayConveyerMissions.size();

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
                        .build());
            }else if(amrInfo.getAmrStatus() == AmrStatus.PROCESSING){
                processingNum++;
            }
        }

        int totalUsagePercent = 0;

        if(processingNum > 0) totalUsagePercent = ((processingNum/50)*100);

        DashboardDto dashboardDto = DashboardDto.builder()
                .todayTotalOutput(todayTotalOutput)
                .totalUsagePercent(totalUsagePercent)
                .realtimeBottleneck(realtimeBottleneckList)
                .realtimeError(realtimeErrorList)
                .build();

        return dashboardDto;
    }
}
