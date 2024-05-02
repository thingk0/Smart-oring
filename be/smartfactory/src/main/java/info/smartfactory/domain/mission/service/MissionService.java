package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
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

    @Scheduled(cron = "0/5 * * * * ?")
    public Mission generateMission() {
        List<Destination> destinationList = destinationRepository.getDestination();
        List<Storage> storageList = storageRepository.getStorage();

                System.out.println("Test Scheduled");

        int submissionNum = 3;
        return missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
    }
}
