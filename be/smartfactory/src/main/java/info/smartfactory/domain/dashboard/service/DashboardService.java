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
import java.time.LocalDateTime;
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

        /////////////////////
        // 생산량 - 오늘 실시간, 어제 데이터
        //어제와 오늘 데이터 중 미션 Type이 CONVEYER_TO_DESTINATION인 데이터
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDateTime yesterdayStart = today.minusDays(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        List<MissionStatusDto> missionList = missionRepository.getCompleteMissions(yesterdayStart, now);

        // 어제 완성품 옮긴 미션 리스트
        List<MissionStatusDto> yesterdayConveyerMissions = missionList.stream()
                .filter(mission -> mission.getMission().getMissionStartedAt().toLocalDate().equals(yesterday))
                .filter(mission -> mission.getMission().getMissionType() == MissionType.CONVEYOR_TO_DESTINATION)
                .collect(Collectors.toList());

        // 오늘 수행한 전체 미션 리스트
        List<MissionStatusDto> todayMissions = missionList.stream()
                .filter(mission -> mission.getMission().getMissionStartedAt().toLocalDate().equals(today))
                .collect(Collectors.toList());

        // 오늘 수행한 미션 중 완성품 옮긴 미션 리스트
        List<MissionStatusDto> todayConveyerMissions = todayMissions.stream()
                .filter(mission -> mission.getMission().getMissionType() == MissionType.CONVEYOR_TO_DESTINATION)
                .collect(Collectors.toList());


        ////////////////////
        // 오늘 생산량
        long todayTotalOutput = todayConveyerMissions.size();


        ///////////////////////////
        // 가동룰 - 전체 Amr 중에 실시간 PROCESSING, BOTTLENECK인 것의 비율
        int processingNum = 0;

        ////////////////////////////
        // amr별 사용률
        ////////////////////////////

        int todayMissionCnt = todayMissions.size();
        List<AmrPercentDto> amrUsagePercent = new ArrayList<AmrPercentDto>();

        // amrId별로 미션을 그룹화하고 각 amrId의 출현 횟수를 계산
        Map<Long, Long> amrIdCount = todayMissions.stream()
                .collect(Collectors.groupingBy(mission -> mission.getMission().getAmr().getId(), Collectors.counting()));

        // 출현 횟수가 가장 낮은 상위 3개의 amrId와 그 횟수를 선택
        List<Map.Entry<Long, Long>> leastFrequentAmrIds = amrIdCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(3)
                .collect(Collectors.toList());

        leastFrequentAmrIds.forEach(entry ->
                amrUsagePercent.add(AmrPercentDto.builder()
                       .amrId(entry.getKey())
                       .percentage(entry.getValue() * 100 / todayMissionCnt)
                       .build()));

        /////////////////////////////////
        // amr별 에러율
        ////////////////////////////////

        // amrId별로 미션 수를 계산
        Map<Long, Long> totalMissionsPerAmr = todayMissions.stream()
                .collect(Collectors.groupingBy(mission -> mission.getMission().getAmr().getId(), Collectors.counting()));

        // amrId별로 에러가 발생한 미션 수를 계산
        Map<Long, Long> errorMissionsPerAmr = todayMissions.stream()
                .filter(mission -> mission.isHasError()) // 에러가 있는 미션만 필터링
                .collect(Collectors.groupingBy(mission -> mission.getMission().getAmr().getId(), Collectors.counting()));

        // amr별 에러율 계산
        List<AmrPercentDto> amrErrorRate = new ArrayList<>();

        totalMissionsPerAmr.forEach((amrId, totalCount) -> {
            Long errorCount = errorMissionsPerAmr.getOrDefault(amrId, 0L);
            double errorRate = (double) errorCount / totalCount * 100; // 에러율 계산

            amrErrorRate.add(AmrPercentDto.builder()
                    .amrId(amrId)
                    .percentage(errorRate)
                    .build());
        });

        // 결과 출력
        amrErrorRate.forEach(amr ->
                System.out.println("AMR ID: " + amr.getAmrId() + ", Error Rate: " + amr.getPercentage() + "%"));



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
