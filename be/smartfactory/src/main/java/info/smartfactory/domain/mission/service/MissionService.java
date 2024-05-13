package info.smartfactory.domain.mission.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.producer.MissionProducer;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.mission.repository.SubmissionRepository;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import info.smartfactory.domain.mission.service.dto.MissionInfoDto;
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

    private final MissionGenerator missionGenerator;
    private final DestinationRepository destinationRepository;
    private final StorageRepository storageRepository;
    private final MissionRepository missionRepository;
    private final SubmissionRepository submissionRepository;
    private final MissionMapper missionMapper;
    private final ConveyorBeltRepository conveyorBeltRepository;
    private final MissionProducer kafkaProducer;
    private final AmrHistoryRepository amrHistoryRepository;

    @Scheduled(cron = "0/10 * * * * ?")
    public void generateMission() {

        List<Storage> storageList = storageRepository.findAll();
        List<ConveyorBelt> conveyerbeltList = conveyorBeltRepository.findAll();
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

//        MissionDto missionDto = missionMapper.toDto(mission);

        Mission missionWithNodes = missionRepository.findByMissionIdWithNodes(missionId);

        System.out.println(missionWithNodes.toString());
        log.info("============================================");
        log.info(missionWithNodes.toString());

        return missionMapper.toDto(mission);
    }

    public void completeMission(MissionKafkaDto missionKafkaDto) {
        Mission mission = missionRepository.findById(missionKafkaDto.id())
                                           .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + missionKafkaDto.id()));

        mission.completeMission(
            mission.getMissionStartedAt(),
            mission.getMissionFinishedAt(),
            mission.getMissionEstimatedTime(),
            mission.getFullPath()
        );
    }

    @Transactional(readOnly = true)
    public Page<MissionHistoryDto> getMissionHistories(
        Pageable pageable, String amrType, LocalDateTime startTime, LocalDateTime endTime, Integer bottleneckSeconds
    ) {
        return missionRepository.fetchMissionHistories(pageable, amrType, startTime, endTime, bottleneckSeconds);
    }

    @Transactional(readOnly = true)
    public MissionInfoDto getMissionHistoryAnalysisInfo(Long missionId) {
        return amrHistoryRepository.fetchMissionAnalysisInfo(missionId);
    }
}
