package info.smartfactory.domain.mission.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.producer.MissionProducer;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.mission.repository.SubmissionRepository;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.AmrStatusTimeLine;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.AmrStatusWithTime;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.MissionExecutionTimeAnalysisDto;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import info.smartfactory.domain.mission.service.dto.MissionKafkaDto;
import info.smartfactory.domain.node.entity.type.ConveyorBelt;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import info.smartfactory.domain.node.repository.ConveyorBeltRepository;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.global.util.mission.MissionGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {

    private final AmrRepository amrRepository;
    private final MissionRepository missionRepository;
    private final StorageRepository storageRepository;
    private final SubmissionRepository submissionRepository;
    private final AmrHistoryRepository amrHistoryRepository;
    private final DestinationRepository destinationRepository;
    private final ConveyorBeltRepository conveyorBeltRepository;

    private final ObjectMapper objectMapper;
    private final MissionMapper missionMapper;
    private final MissionProducer kafkaProducer;
    private final MissionGenerator missionGenerator;

    @Scheduled(cron = "0/10 * * * * ?")
    public void generateMission() {

        List<Storage> storageList = storageRepository.findAll();
        List<ConveyorBelt> conveyerbeltList = conveyorBeltRepository.findByIsInteractiveTrue();
        List<Destination> destinationList = destinationRepository.findAll();

        int maxStopoverNum = 3;

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomStopoverNum = random.nextInt(maxStopoverNum + 1);

        log.info("Test Scheduled");

        if ((storageList.size() >= randomStopoverNum + 2) && (!conveyerbeltList.isEmpty()) && (!destinationList.isEmpty())) {
            Mission mission = missionGenerator.generateRandomMission(randomStopoverNum, storageList, conveyerbeltList, destinationList);
            missionRepository.save(mission);

            List<Submission> submissionList = mission.getSubmissionList();
            submissionRepository.saveAll(submissionList);

            MissionKafkaDTO missionKafkaDTO = MissionKafkaDTO.builder()
                                                             .id(mission.getId())
                                                             .build();

            kafkaProducer.create(missionKafkaDTO);
        }
    }

    @Transactional
    public MissionDto getMissionInfo(Long missionId) {
        // missionId에 해당하는 mission, submission 정보를 반환해줌
        Mission mission = missionRepository.findById(missionId)
                                           .orElseThrow(() -> new RuntimeException("Entity not found with ID : " + missionId));

        return missionMapper.toDto(mission);
    }

    @Transactional
    public void completeMission(MissionKafkaDto missionKafkaDto) throws JsonProcessingException {
        Mission mission = missionRepository.findById(missionKafkaDto.id())
                                           .orElseThrow(() -> new RuntimeException("Mission Entity not found with ID: " + missionKafkaDto.id()));

        Amr amr = amrRepository.findById(missionKafkaDto.amrId())
                               .orElseThrow(() -> new RuntimeException("Amr Entity not found with ID: " + missionKafkaDto.amrId()));

        mission.completeMission(
            missionKafkaDto.missionStartedAt(),
            missionKafkaDto.missionFinishedAt(),
            missionKafkaDto.missionEstimatedTime(),
            objectMapper.writeValueAsString(missionKafkaDto.fullPath()),
            amr
        );
    }

    @Transactional(readOnly = true)
    public Page<MissionHistoryDto> getMissionHistories(Pageable pageable,
                                                       List<String> amrType,
                                                       LocalDateTime startTime,
                                                       LocalDateTime endTime,
                                                       Integer bottleneckSeconds
    ) {
        return missionRepository.fetchMissionHistories(pageable, amrType, startTime, endTime, bottleneckSeconds);
    }


    /**
     * 미션 이력 분석 정보를 조회합니다.
     *
     * @param missionId 미션 ID
     * @return 미션 분석 데이터를 포함하는 MissionAnalysisDto 객체
     */
    @Transactional(readOnly = true)
    public MissionAnalysisDto getMissionHistoryAnalysisInfo(Long missionId) {

        // 미션 실행 시간 분석 데이터 가져오기
        MissionExecutionTimeAnalysisDto missionExecutionTimeAnalysis = amrHistoryRepository.fetchMissionAnalysisInfo(missionId);

        // AMR 상태 및 시간 데이터 가져오기
        List<AmrStatusWithTime> list = amrHistoryRepository.fetchAmrStatusWithTime(missionId);

        // AMR 상태 타임라인 생성
        List<AmrStatusTimeLine> amrStatusTimeline = createAmrStatusTimeline(list);

        return MissionAnalysisDto.builder()
                                 .missionExecutionTimeAnalysis(missionExecutionTimeAnalysis)
                                 .amrStatusTimeline(amrStatusTimeline)
                                 .build();
    }

    /**
     * AMR 상태 변경 타임라인을 생성합니다.
     *
     * @param list AmrStatusWithTime 리스트
     * @return 상태 변경 타임라인을 나타내는 AmrStatusTimeLine 리스트
     */
    private static List<AmrStatusTimeLine> createAmrStatusTimeline(List<AmrStatusWithTime> list) {

        List<AmrStatusTimeLine> timeline = new ArrayList<>();

        // 리스트가 비어있는 경우 빈 타임라인 반환
        if (list.isEmpty()) {
            return timeline;
        }

        AmrStatusWithTime start = list.get(0);
        AmrStatus previousStatus = start.getAmrStatus();
        LocalDateTime previousTime = start.getAmrHistoryCreatedAt();

        // 초기 시간 설정
        List<Long> initialTimes = new ArrayList<>();
        initialTimes.add(previousTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        // 리스트 순회
        for (int i = 1; i < list.size(); i++) {

            AmrStatusWithTime current = list.get(i);
            AmrStatus currentStatus = current.getAmrStatus();
            LocalDateTime currentTime = current.getAmrHistoryCreatedAt();

            // 상태 변경 시 시간 추가 및 타임라인 생성
            if (!currentStatus.equals(previousStatus)) {
                initialTimes.add(currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                timeline.add(of(previousStatus, initialTimes));

                initialTimes = new ArrayList<>();
                initialTimes.add(currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                previousStatus = currentStatus;
            }

            previousTime = currentTime;
        }

        // 마지막 상태의 종료 시간 추가
        initialTimes.add(previousTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        timeline.add(of(previousStatus, initialTimes));

        return timeline;
    }

    /**
     * AmrStatusTimeLine 객체를 생성합니다.
     *
     * @param previousStatus 이전 AMR 상태
     * @param initialTimes   시작과 끝 시간을 포함하는 리스트
     * @return AmrStatusTimeLine 객체
     */
    private static AmrStatusTimeLine of(AmrStatus previousStatus, List<Long> initialTimes) {
        return AmrStatusTimeLine.builder()
                                .amrStatus(previousStatus)
                                .startToEnd(initialTimes)
                                .build();
    }


}
