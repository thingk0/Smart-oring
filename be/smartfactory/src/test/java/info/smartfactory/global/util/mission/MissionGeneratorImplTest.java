package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

//@SpringBootTest
@Transactional
class MissionGeneratorImplTest {

    //@Autowired
    MissionGeneratorImpl missionGenerator = new MissionGeneratorImpl();


    @Test
    public void 미션생성() throws Exception {
        // Given
        List<Storage> storageList = new ArrayList<>();

        Storage storage1 = Storage.createStorage(0, 0, EntranceDirection.EAST);
        Storage storage2 = Storage.createStorage(1, 1, EntranceDirection.SOUTH);
        Storage storage3 = Storage.createStorage(2, 2, EntranceDirection.NORTH);
        Storage storage4 = Storage.createStorage(3, 3, EntranceDirection.NORTH);
        Storage storage5 = Storage.createStorage(4, 4, EntranceDirection.NORTH);
        Storage storage6 = Storage.createStorage(5, 5, EntranceDirection.NORTH);

        storageList.add(storage1);
        storageList.add(storage2);
        storageList.add(storage3);
        storageList.add(storage4);
        storageList.add(storage5);
        storageList.add(storage6);

        List<Destination> destinationList = new ArrayList<>();

        Destination destination1 = Destination.createDestination(6, 6, EntranceDirection.EAST);
        Destination destination2 = Destination.createDestination(7, 7, EntranceDirection.EAST);
        Destination destination3 = Destination.createDestination(8, 8, EntranceDirection.EAST);

        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination3);

        // When
        Mission mission = missionGenerator.generateRandomMission(3, destinationList, storageList);

        // Then

    }

}