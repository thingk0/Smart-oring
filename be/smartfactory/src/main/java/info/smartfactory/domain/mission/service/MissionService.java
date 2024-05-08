package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.kafka.KafkaProducer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(cron = "0/10 * * * * ?")
    public Mission generateMission() {
        List<Destination> destinationList = destinationRepository.findAll();
        List<Storage> storageList = storageRepository.findAll();

        log.info("Test Scheduled");

        int submissionNum = 3;
        Mission mission = null;
        if(storageList.size() > submissionNum && destinationList.size() > 0){
            mission = missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
            //TODO : 생성된 미션 디비에 저장하기 **
//            List<Submission> submissionList = mission.getSubmissionList();
//            for(Submission submission : submissionList) {
//                System.out.println(submission.getArriveNode().getXCoordinate()+ " " + submission.getArriveNode().getYCoordinate());
//            }
            System.out.println("==========");

            MissionKafkaDTO missionKafkaDTO = MissionKafkaDTO.builder()
                    .id(mission.getId())
                    .build();

            kafkaProducer.create(missionKafkaDTO);
        }

        return mission;
    }

    public MissionDto getMissionInfo(Long missionId) {
        // missionId에 해당하는 mission, submission 정보를 반환해줌
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID : " + missionId));

//        MissionDto missionDto = missionMapper.toDto(mission);


        Mission missionWithNodes = missionRepository.findByMissionIdWithNodes(missionId);


        MissionDto missionDto = missionMapper.toDto(mission);


        return missionDto;
    }

    public void completeMission(MissionKafkaDto missionKafkaDto) {
        Mission mission = missionRepository.findById(missionKafkaDto.id()).orElseThrow(() -> new RuntimeException("Entity not found with ID: " + missionKafkaDto.id()));

        mission.modifyMission(
                mission.getMissionStartedAt(),
                mission.getMissionFinishedAt(),
                mission.getMissionEstimatedTime(),
                mission.getFullPath()
        );
    }
}
