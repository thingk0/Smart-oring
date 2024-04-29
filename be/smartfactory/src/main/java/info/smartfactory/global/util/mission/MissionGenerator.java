package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;

public interface MissionGenerator {
    Mission generateRandomMission(); //랜덤으로 미션 생성 후 DB에 저장하고 Mission 반환

}

