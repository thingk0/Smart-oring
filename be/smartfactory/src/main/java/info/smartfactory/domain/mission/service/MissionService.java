package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.global.util.mission.MissionGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionGenerator missionGenerator;
    private final DestinationRepository destinationRepository;
    private final StorageRepository storageRepository;
    private final MissionRepository missionRepository;

    @Scheduled(cron = "0/5 * * * * ?")
    public Mission generateMission() {

        List<Destination> destinationList = destinationRepository.findAll();
        List<Storage> storageList = storageRepository.findAll();

        log.info("Test Scheduled");

        int submissionNum = 3;
        return missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
    }

    public Mission findMissionByIdOrThrow(Long missionId) {
        return missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
    }
}
