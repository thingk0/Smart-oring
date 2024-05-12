package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.producer.MissionProducer;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.mission.repository.SubmissionRepository;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.MissionKafkaDto;
import info.smartfactory.domain.node.entity.type.ConveyerBelt;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import info.smartfactory.domain.node.repository.ConveyerBeltRepository;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.global.util.mission.MissionGenerator;
import info.smartfactory.global.util.mission.MissionGeneratorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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
    private final ConveyerBeltRepository conveyerBeltRepository;
    private final MissionProducer kafkaProducer;

    @Scheduled(cron = "0/10 * * * * ?")
    public Mission generateMission() {
        List<Storage> storageList = storageRepository.findAll();
        List<ConveyerBelt> conveyerbeltList = conveyerBeltRepository.findAll();
        List<Destination> destinationList = destinationRepository.findAll();

        int maxStopoverNum = 2;

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomStopoverNum = random.nextInt(maxStopoverNum+1);

        log.info("Test Scheduled");

        if((storageList.size() >= randomStopoverNum+2) && (!conveyerbeltList.isEmpty()) && (!destinationList.isEmpty())) {
            Mission mission = missionGenerator.generateRandomMission(randomStopoverNum, storageList, conveyerbeltList, destinationList);
            missionRepository.save(mission);

            List<Submission> submissionList = mission.getSubmissionList();
            submissionRepository.saveAll(submissionList);

            MissionKafkaDTO missionKafkaDTO = MissionKafkaDTO.builder()
                    .id(mission.getId())
                    .build();

            kafkaProducer.create(missionKafkaDTO);

            return mission;
        }

        return null;

//        else {
//            throw new RuntimeException("미션을 생성할 수 없는 환경입니다.");
//        }
//        if(storageList.size() > submissionNum && destinationList.size() > 0){
//            mission = missionGenerator.generateRandomMission(submissionNum, destinationList, storageList);
//            missionRepository.save(mission);
//
//            List<Submission> submissionList = mission.getSubmissionList();
//            for (Submission submission : submissionList) {
//                submissionRepository.save(submission);
////                System.out.println(submission.getArriveNode().getXCoordinate()+ " " + submission.getArriveNode().getYCoordinate());
//            }
////            System.out.println("==========");
//
//            MissionKafkaDTO missionKafkaDTO = MissionKafkaDTO.builder()
//                    .id(mission.getId())
//                    .build();
//
//            kafkaProducer.create(missionKafkaDTO);
//        }
//
//        return mission;
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
        Mission mission = missionRepository.findById(missionKafkaDto.id()).orElseThrow(() -> new RuntimeException("Entity not found with ID: " + missionKafkaDto.id()));

        mission.completeMission(
                mission.getMissionStartedAt(),
                mission.getMissionFinishedAt(),
                mission.getMissionEstimatedTime(),
                mission.getFullPath()
        );
    }
}
