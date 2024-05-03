package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
public class MissionGeneratorImpl implements MissionGenerator {

    @Override
    public Mission generateRandomMission(int submissionNum, List<Destination> destinations, List<Storage> storages) {
        //nodes들 중 랜덤으로 submissionNum 개수만큼 생성해줌

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < submissionNum; i++) { //필요한 만큼 서브미션 생성
            while (true) {
                Random random = new Random();
                random.setSeed(System.currentTimeMillis());
                int size = storages.size();
                if (size <= 0) {
                    size = 1;
                }
                int randomIdx = random.nextInt(size); // 창고 랜덤 인덱스 생성

                if (set.add(randomIdx)) {
                    break; // 저장되어 있지 않은 창고라면 추가
                }
            }
        }

        // set 제대로 숫자가 뽑혔는지 확인 코드
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println("set : " + iterator.next());
        }

        // 위에서 뽑힌 창고 인덱스 순서대로 submission을 만들어줌
        Mission mission = Mission.createMission();
        //List<Submission> submissionList = new ArrayList<>();
        int order = 0;
        for (int index : set) {
            // submission 개수만큼 랜덤으로 갈 곳 생성했으니 객체 생성해줌
            Submission submission = Submission.createSubmission(
                storages.get(index),
                ++order
            );
            mission.addSubmission(submission);
        }

        // 마지막으로 도착지를 랜덤으로 생성
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomIdx = random.nextInt(destinations.size()); // 도착지 랜덤 인덱스 생성

        // 도착지 노드 생성
        Submission destSubmission = Submission.createSubmission(
            destinations.get(randomIdx),
            submissionNum + 1
        );
        //submissionList.add(destSubmission); //submission list에 추가해줌
        mission.addSubmission(destSubmission);

        //submission list를 mission에 저장해줌
        //mission.addSubmission(submission);

        List<Submission> submissionList = mission.getSubmissionList();

        for (Submission submission : submissionList) {
            System.out.print("x : " + submission.getArriveNode().getXCoordinate());
            System.out.println("y : " + submission.getArriveNode().getYCoordinate());
        }

        return mission;
    }

}
