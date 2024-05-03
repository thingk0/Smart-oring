package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.kafka.KafkaProducer;
import info.smartfactory.domain.mission.repository.MissionRepository;
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

    public void getMissionInfo() {
    }
}
