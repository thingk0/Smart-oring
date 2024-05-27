package info.smartfactory.global.util.mission;

import java.util.List;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.entity.type.ConveyorBelt;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;

public interface MissionGenerator {

//    Mission generateRandomMission(int submissionNum, List<Destination> destinations,
//                                  List<Storage> storages); //랜덤으로 미션 생성 후 DB에 저장하고 Mission 반환

    Mission generateRandomMission(int stopoverNum, List<Storage> storages, List<ConveyorBelt> conveyorBelts,
        List<Destination> destinations);


}

