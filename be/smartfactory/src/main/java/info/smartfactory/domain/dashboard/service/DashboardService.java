package info.smartfactory.domain.dashboard.service;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.ErrorDto;
import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import info.smartfactory.domain.mission.repository.MissionRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    public DashboardDto getRealtimeData() {

        /////////////////////
        // 생산량 - 오늘 실시간, 어제 데이터

        //어제와 오늘 데이터 중 미션 Type이 CONVEYOR_TO_DESTINATION인 데이터
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDateTime yesterdayStart = today.minusDays(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        System.out.println("yesterday start: " + yesterdayStart);

        List<MissionStatusDto> missionList = missionRepository.getCompleteMissions(yesterdayStart, now);

        for(MissionStatusDto mission: missionList){
            System.out.println("mission "+mission.getMission().getMissionType()+" "+mission.isHasError());
        }

        // 어제 완성품 옮긴 미션 리스트
        List<MissionStatusDto> yesterdayConveyerMissions = missionList.stream()
                                                                      .filter(mission -> yesterday.isEqual(mission.getMission().getMissionStartedAt().toLocalDate()))
                                                                      .filter(mission -> mission.getMission().getMissionType() == MissionType.CONVEYOR_TO_DESTINATION)
                                                                      .collect(Collectors.toList());

        long[] yesterdayOutputGraph = countMissionsByTimeInterval(yesterdayConveyerMissions);

        // 오늘 수행한 전체 미션 리스트
        List<MissionStatusDto> todayMissions = missionList.stream()
                                                          .filter(mission -> today.isEqual(mission.getMission().getMissionStartedAt().toLocalDate()))
                                                          .collect(Collectors.toList());

        // 오늘 수행한 미션 중 완성품 옮긴 미션 리스트
        List<MissionStatusDto> todayConveyerMissions = todayMissions.stream()
                                                                    .filter(mission -> mission.getMission().getMissionType()
                                                                        == MissionType.CONVEYOR_TO_DESTINATION)
                                                                    .collect(Collectors.toList());

        long[] todayOutputGraph = countMissionsByTimeInterval(todayConveyerMissions);

        // 오늘 생산량
        long todayTotalOutput = todayConveyerMissions.size();

        // amr별 사용률
        List<AmrPercentDto> amrUsagePercent = amrUsagePercent(todayMissions);

        // amr별 에러율
        List<AmrPercentDto> amrErrorRate = amrError(todayMissions);

        // 실시간 에러, 병목

        List<ErrorDto> realtimeErrorList = new ArrayList<>();
        List<BottleneckDto> realtimeBottleneckList = new ArrayList<>();

        // 가동룰 - 전체 Amr 중에 실시간 PROCESSING, BOTTLENECK인 것의 비율
        int processingNum = 0;

        List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();

        for (CurrentAmrInfoRedisDto amrInfo : all) {
            if (amrInfo!= null && amrInfo.getAmrStatus() == AmrStatus.BOTTLENECK) {
                processingNum++;
                System.out.println("Processing");
                realtimeBottleneckList.add(BottleneckDto.builder()
                                                        .missionId(amrInfo.getMissionId())
                                                        .amrId(amrInfo.getAmrId())
                                                        .xCoordinate(amrInfo.getXCoordinate())
                                                        .yCoordinate(amrInfo.getYCoordinate())
                                                        .bottleneckPeriod(amrInfo.getCurrentStopDuration())
                                                        .bottleneckCreatedAt(amrInfo.getAmrHistoryCreatedAt())
                                                        .build());
            } else if (amrInfo!= null && amrInfo.getAmrStatus() == AmrStatus.ERROR) {
                realtimeErrorList.add(ErrorDto.builder()
                                              .missionId(amrInfo.getMissionId())
                                              .amrId(amrInfo.getAmrId())
                                              .xCoordinate(amrInfo.getXCoordinate())
                                              .yCoordinate(amrInfo.getYCoordinate())
                                              .build());
            } else if (amrInfo!= null && amrInfo.getAmrStatus() == AmrStatus.PROCESSING) {
                processingNum++;
                System.out.println("Processing");
            }
        }

        int totalUsagePercent = 0;

        System.out.println("=========================Processing Num "+ processingNum);
        System.out.println("=========================전체 사이즈 "+ all.size());
        if (processingNum > 0) {
            totalUsagePercent = ((processingNum / all.size()) * 100);
        }

        return DashboardDto.builder()
                           .yesterdayOutputGraph(yesterdayOutputGraph)
                           .todayOutputGraph(todayOutputGraph)
                           .todayTotalOutput(todayTotalOutput)
                           .totalUsagePercent(totalUsagePercent)
                           .amrUsagePercent(amrUsagePercent)
                           .amrErrorPercent(amrErrorRate)
                           .realtimeBottleneck(realtimeBottleneckList)
                           .realtimeError(realtimeErrorList)
                           .build();
    }

    public long[] countMissionsByTimeInterval(List<MissionStatusDto> missions) {
        LocalTime startTime = LocalTime.MIN;
        LocalTime endTime = LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES);

        Map<LocalTime, Long> counts = missions.stream()
                                              .collect(Collectors.groupingBy(mission -> {
                                                  LocalTime time = mission.getMission().getMissionStartedAt().toLocalTime()
                                                                          .truncatedTo(ChronoUnit.MINUTES);
                                                  int minutes = time.getMinute();
                                                  int mod = minutes % 30;
                                                  return time.minusMinutes(mod);
                                              }, Collectors.counting()));

        long totalIntervals = Duration.between(startTime, endTime).toMinutes() / 30;
        long[] missionCounts = new long[(int) totalIntervals];

        for (int i = 0; i < totalIntervals; i++) {
            LocalTime intervalStart = startTime.plusMinutes(30 * i);
            missionCounts[i] = counts.getOrDefault(intervalStart, 0L).intValue();
        }

        return missionCounts;
    }

    public List<AmrPercentDto> amrUsagePercent(List<MissionStatusDto> todayMissions) {
        int todayMissionCnt = todayMissions.size();
        List<AmrPercentDto> amrUsagePercent = new ArrayList<>();

        // amrId별로 미션을 그룹화하고 각 amrId의 출현 횟수를 계산
        Map<Long, Long> amrIdCount = todayMissions.stream()
                .collect(
                        Collectors.groupingBy(mission -> {
                            Amr amr = mission.getMission().getAmr();
                            return amr.getId();
                        }, Collectors.counting()));

        // 각 amrId의 출현 횟수를 사용하여 사용률을 계산
        amrIdCount.forEach((amrId, cnt) ->
                amrUsagePercent.add(AmrPercentDto.builder()
                        .amrId(amrId)
                        .percentage(Math.round(((double) cnt / todayMissionCnt) * 100))
                        .build()));

        // 출현 횟수가 가장 낮은 상위 3개의 amrId와 그 사용률을 선택
        return amrUsagePercent.stream()
                .sorted(Comparator.comparingDouble(AmrPercentDto::getPercentage))
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<AmrPercentDto> amrError(List<MissionStatusDto> todayMissions) {
        // amrId별로 미션 수를 계산
        Map<Long, Long> totalMissionsPerAmr = todayMissions.stream()
                .collect(Collectors.groupingBy(mission -> mission.getMission().getAmr().getId(),
                        Collectors.counting()));

        // amrId별로 에러가 발생한 미션 수를 계산
        Map<Long, Long> errorMissionsPerAmr = todayMissions.stream()
                .filter(MissionStatusDto::isHasError) // 에러가 있는 미션만 필터링
                .collect(Collectors.groupingBy(mission -> mission.getMission().getAmr().getId(),
                        Collectors.counting()));

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

        return amrErrorRate.stream()
                .sorted(Comparator.comparingDouble(AmrPercentDto::getPercentage).reversed()) // 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .collect(Collectors.toList());
    }

}
