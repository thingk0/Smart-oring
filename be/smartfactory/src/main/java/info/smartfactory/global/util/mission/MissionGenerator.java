package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.entity.Node;

public interface MissionGenerator {
    Mission generateRandomMission(int submissionNum, Node[] destinations, Node[] storages); //랜덤으로 미션 생성 후 DB에 저장하고 Mission 반환

}

