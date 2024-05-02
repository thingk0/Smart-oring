package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.domain.node.entity.Destination;
import info.smartfactory.domain.node.entity.Storage;
import info.smartfactory.global.util.mission.MissionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService {
    @Autowired
    MissionGenerator missionGenerator;

    @Autowired
    DestinationRepository destinationRepository;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(cron = "0/10 * * * * ?")
    public Mission generateMission() {
        List<Destination> destinationList = destinationRepository.getDestination();
        List<Storage> storageList = storageRepository.getStorage();

        System.out.println("Test Scheduled");

        int submissionNum = 3;
        Mission mission = null;
        if(storageList.size() > submissionNum && destinationList.size() > 0){
            mission = missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
            List<Submission> submissionList = mission.getSubmissionList();
//            for(Submission submission : submissionList) {
//                System.out.println(submission.getArriveNode().getXCoordinate()+ " " + submission.getArriveNode().getYCoordinate());
//            }
            System.out.println("==========");

            kafkaProducer.create(mission);
        }

        return mission;
    }
}
