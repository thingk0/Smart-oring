package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.node.entity.Node;

import java.util.*;

public abstract class MissionGeneratorImpl implements MissionGenerator {

    @Override
    public Mission generateRandomMission(int submissionNum, Node[] destinations, Node[] storages) {
        //nodes들 중 랜덤으로 submissionNum 개수만큼 생성해줌


        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < submissionNum; i++) { //필요한 만큼 서브미션 생성
            while (true) {
                Random random = new Random();
                random.setSeed(System.currentTimeMillis());
                int randomIdx = random.nextInt(storages.length); // 창고 랜덤 인덱스 생성

                if (set.add(randomIdx)) break; // 저장되어 있지 않은 창고라면 추가
            }
        }

        // set 제대로 숫자가 뽑혔는지 확인 코드
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println("set : " + iterator.next());
        }

        // 위에서 뽑힌 창고 인덱스 순서대로 submission을 만들어줌
        Mission mission = new Mission();
        List<Submission> submissionList = new ArrayList<>();
        int order = 0;
        for (int index : set) {
            // submission 개수만큼 랜덤으로 갈 곳 생성했으니 객체 생성해줌
            Submission submission = new Submission();
            submission.setMission(mission);
            submission.setSubmissionOrder(++order);
            submission.setArriveNode(storages[index]);

            submissionList.add(submission);
        }


        // 마지막으로 도착지를 랜덤으로 생성
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomIdx = random.nextInt(destinations.length); // 도착지 랜덤 인덱스 생성

        // 도착지 노드 생성
        Submission destSubmission = new Submission();
        destSubmission.setMission(mission);
        destSubmission.setSubmissionOrder(submissionNum + 1);
        destSubmission.setArriveNode(destinations[randomIdx]);

        submissionList.add(destSubmission); //submission list에 추가해줌

        //submission list를 mission에 저장해줌
        mission.setSubmissionList(submissionList);

        return mission;
    }

}
