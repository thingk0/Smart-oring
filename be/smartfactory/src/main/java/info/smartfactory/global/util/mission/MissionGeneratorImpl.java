package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import info.smartfactory.domain.node.entity.type.ConveyerBelt;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MissionGeneratorImpl implements MissionGenerator {

//    private static final int RANDOM_STORAGE_NUM = 3; // 중간 자재창고 경유지 랜덤 개수 (해당 수의 미만 개만큼 생성)

//    @Override
//    public Mission generateRandomMission(int submissionNum, List<Destination> destinations, List<Storage> storages) {
//        //nodes들 중 랜덤으로 submissionNum 개수만큼 생성해줌
//        HashSet<Integer> set = new HashSet<>();
//        for (int i = 0; i < submissionNum; i++) { //필요한 만큼 서브미션 생성
//            while (true) {
//                Random random = new Random();
//                random.setSeed(System.currentTimeMillis());
//                int randomIdx = random.nextInt(storages.size()); // 창고 랜덤 인덱스 생성
//                if (set.add(randomIdx)) break; // 저장되어 있지 않은 창고라면 추가
//            }
//        }
//
//        // set 제대로 숫자가 뽑혔는지 확인 코드
//        Iterator<Integer> iterator = set.iterator();
//        while (iterator.hasNext()) {
//            System.out.println("set : " + iterator.next());
//        }
//
//        // 위에서 뽑힌 창고 인덱스 순서대로 submission을 만들어줌
//        Mission mission = Mission.createMission();
//
//        int order = 0;
//        for (int index : set) {
//            // submission 개수만큼 랜덤으로 갈 곳 생성했으니 객체 생성해줌
//            Submission submission = Submission.createSubmission(
//                    storages.get(index),
//                    ++order
//            );
//            mission.addSubmission(submission);
//        }
//
//
//        // 마지막으로 도착지를 랜덤으로 생성
//        Random random = new Random();
//        random.setSeed(System.currentTimeMillis());
//        int randomIdx = random.nextInt(destinations.size()); // 도착지 랜덤 인덱스 생성
//
//        // 도착지 노드 생성
//        Submission destSubmission = Submission.createSubmission(
//                destinations.get(randomIdx),
//                submissionNum + 1
//        );
//        mission.addSubmission(destSubmission);
//
//        return mission;
//    }

    @Override
    public Mission generateRandomMission(int stopoverNum, List<Storage> storages, List<ConveyerBelt> conveyerBelts, List<Destination> destinations) {
        // MissionType 중 하나를 랜덤으로 뽑음
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
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
            random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(storages.size());

            Submission startSubmission = Submission.createSubmission(
                    storages.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // n개의 자재창고 경유지 설정 + 도착지까지 결정됨
            generateRandomStopover(mission, order, startIdx, storages, stopoverNum);
        }
        else if(randomIdx == MissionType.STORAGE_TO_CONVEYER.ordinal()){ // 자재창고 -> 컨베이어벨트
            // 출발지 : 자재창고
            // 도착지 : 컨베이어 벨트
            // 중간에 들르는 곳 : n개의 자재창고

            // MissionType 설정
            mission.modifyMissionType(MissionType.STORAGE_TO_CONVEYER);

            // 출발지
            random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(storages.size());

            Submission startSubmission = Submission.createSubmission(
                    storages.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // n개의 자재창고 경유지
            generateRandomStopover(mission, order, startIdx, storages, stopoverNum);

            // 도착지
            random.setSeed(System.currentTimeMillis());
            int endIdx = random.nextInt(conveyerBelts.size());

            Submission endSubmission = Submission.createSubmission(
                    conveyerBelts.get(endIdx),
                    ++order
            );
            mission.addSubmission(endSubmission);

        }
        else if(randomIdx == MissionType.CONVEYER_TO_DESTINATION.ordinal()){ // 컨베이어벨트 -> 완성품보관 창고
            // 출발지 : 컨베이어벨트
            // 도착지 : 완성품보관 창고
            // 중간에 들르는 곳 : 없음

            // MissionType 설정
            mission.modifyMissionType(MissionType.CONVEYER_TO_DESTINATION);

            // 출발지
            random.setSeed(System.currentTimeMillis());
            int startIdx = random.nextInt(conveyerBelts.size());

            Submission startSubmission = Submission.createSubmission(
                    conveyerBelts.get(startIdx),
                    ++order
            );
            mission.addSubmission(startSubmission);

            // 도착지
            random.setSeed(System.currentTimeMillis());
            int endIdx = random.nextInt(destinations.size());

            Submission endSubmission = Submission.createSubmission(
                    destinations.get(endIdx),
                    ++order
            );
            mission.addSubmission(endSubmission);
        }

        return mission;
    }

    private void generateRandomStopover(Mission mission, int order, int startIdx, List<Storage> storages, int stopoverNum) {
        HashSet<Integer> set = new HashSet<>();
        set.add(startIdx);

        for(int i=0; i<stopoverNum; i++) {
            while(true) {
                Random random = new Random();
                random.setSeed(System.currentTimeMillis());
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
    }
}
