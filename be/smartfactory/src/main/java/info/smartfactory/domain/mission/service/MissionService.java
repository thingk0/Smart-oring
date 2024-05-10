package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.producer.MissionProducer;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.mission.repository.SubmissionRepository;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.MissionKafkaDto;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.global.util.mission.MissionGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionService {

    private final MissionGenerator missionGenerator;
    private final DestinationRepository destinationRepository;
    private final StorageRepository storageRepository;
    private final MissionRepository missionRepository;
    private final SubmissionRepository submissionRepository;
    private final MissionMapper missionMapper;
    private final MissionProducer missionProducer;

    @Scheduled(cron = "0/10 * * * * ?")
    public void generateMission() {

        List<Destination> destinationList = destinationRepository.findAll();
        List<Storage> storageList = storageRepository.findAll();

        log.info("Test Scheduled");

        int submissionNum = 3;
        if (storageList.size() > submissionNum && !destinationList.isEmpty()) {
            //TODO : 생성된 미션 디비에 저장하기 **
            Mission mission = missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
            missionRepository.save(mission);
            submissionRepository.saveAll(mission.getSubmissionList());
            missionProducer.create(MissionKafkaDTO.builder()
                                                  .id(mission.getId())
                                                  .build());
        }

    }

    @Transactional
    public MissionDto getMissionInfo(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                                           .orElseThrow(() -> new RuntimeException("Entity not found with ID : " + missionId));
        Mission missionWithNodes = missionRepository.findByMissionIdWithNodes(missionId);

        log.info("============================================");
        log.info(missionWithNodes.toString());
        log.info("============================================");

        return missionMapper.toDto(mission);
    }

    public void completeMission(MissionKafkaDto missionKafkaDto) {
        Mission mission = missionRepository.findById(missionKafkaDto.id())
                                           .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + missionKafkaDto.id()));

        mission.modifyMission(
            mission.getMissionStartedAt(),
            mission.getMissionFinishedAt(),
            mission.getMissionEstimatedTime(),
            mission.getFullPath()
        );
    }
}
