package info.smartfactory.global.util.mission;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import info.smartfactory.domain.node.entity.type.ConveyorBelt;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MissionGeneratorImpl implements MissionGenerator {
    @Override
    public Mission generateRandomMission(int stopoverNum, List<Storage> storages, List<ConveyorBelt> conveyorBelts,
        List<Destination> destinations) {
        // MissionType 중 하나를 랜덤으로 뽑음
        Random random = new Random();
        // random.setSeed(System.currentTimeMillis());
        int randomIdx = random.nextInt(MissionType.values().length);

        Mission mission = Mission.createMission();
        int order = 0;

        if(randomIdx == MissionType.STORAGE_TO_STORAGE.ordinal()){ // 자재창고 -> 자재창고
            // 출발지 : 자채창고
            // 도착지 : 자채창고
            // 중간에 들르는 곳 : n개의 자재창고

            // MissionType 설정
            mission.modifyMissionType(MissionType.STORAGE_TO_STORAGE);

            // 출발지
            // random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(storages.size());

            Submission startSubmission = Submission.createSubmission(
                    storages.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // n개의 자재창고 경유지 설정 + 도착지까지 결정됨
            order = generateRandomStopover(mission, order, startIdx, storages, stopoverNum);
        }
        else if(randomIdx == MissionType.STORAGE_TO_CONVEYOR.ordinal()){ // 자재창고 -> 컨베이어벨트
            // 출발지 : 자재창고
            // 도착지 : 컨베이어 벨트
            // 중간에 들르는 곳 : n개의 자재창고

            // MissionType 설정
            mission.modifyMissionType(MissionType.STORAGE_TO_CONVEYOR);

            // 출발지
            // random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(storages.size());

            Submission startSubmission = Submission.createSubmission(
                    storages.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // n개의 자재창고 경유지
            order = generateRandomStopover(mission, order, startIdx, storages, stopoverNum);

            // 도착지
            List<ConveyorBelt> frontConveyorBelt = ConveyorBelt.getFrontConveyorBelt(conveyorBelts);

            // random.setSeed(System.currentTimeMillis());
            int endIdx = random.nextInt(frontConveyorBelt.size());
            System.out.println("endIdx = " + endIdx);

            Submission endSubmission = Submission.createSubmission(
                frontConveyorBelt.get(endIdx),
                    ++order
            );
            mission.addSubmission(endSubmission);

        }
        else if(randomIdx == MissionType.CONVEYOR_TO_DESTINATION.ordinal()){ // 컨베이어벨트 -> 완성품보관 창고
            // 출발지 : 컨베이어벨트
            // 도착지 : 완성품보관 창고
            // 중간에 들르는 곳 : 없음

            // MissionType 설정
            mission.modifyMissionType(MissionType.CONVEYOR_TO_DESTINATION);

            // 출발지
            List<ConveyorBelt> endConveyorBelt = ConveyorBelt.getEndConveyorBelt(conveyorBelts);

            // random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(endConveyorBelt.size());

            Submission startSubmission = Submission.createSubmission(
                endConveyorBelt.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // 도착지
            // random.setSeed(System.currentTimeMillis());

            int endIdx = random.nextInt(destinations.size());

            Submission endSubmission = Submission.createSubmission(
                    destinations.get(endIdx),
                    ++order
            );
            mission.addSubmission(endSubmission);
        }

        return mission;
    }

    private int generateRandomStopover(Mission mission, int order, int startIdx, List<Storage> storages, int stopoverNum) {
        HashSet<Integer> set = new HashSet<>();
        set.add(startIdx);

        for(int i=0; i<stopoverNum; i++) {
            while(true) {
                Random random = new Random();
                // random.setSeed(System.currentTimeMillis());
                int stopoverIdx = random.nextInt(storages.size()); // 창고 인덱스 생성

                if(set.add(stopoverIdx)) { // 저장되지 않은 적재창고라면 set과 misson에 추가
                    Submission submission = Submission.createSubmission(
                            storages.get(stopoverIdx),
                            ++order
                    );

                    mission.addSubmission(submission);

                    break;
                }
            }
        }

        return order;
    }
}
