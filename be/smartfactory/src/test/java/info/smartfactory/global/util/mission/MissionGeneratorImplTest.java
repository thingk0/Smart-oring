package info.smartfactory.global.util.mission;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.entity.Destination;
import info.smartfactory.domain.node.entity.Storage;
import info.smartfactory.domain.node.entity.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
@Transactional
class MissionGeneratorImplTest {
    //@Autowired
    MissionGeneratorImpl missionGenerator = new MissionGeneratorImpl();
    
    
    @Test
    public void 미션생성() throws Exception {
        // Given
        List<Storage> storageList = new ArrayList<>();

        Storage storage1 = Storage.createStorage(0,0, Direction.East);
        Storage storage2 = Storage.createStorage(1, 1, Direction.South);
        Storage storage3 = Storage.createStorage(2, 2, Direction.North);
        Storage storage4 = Storage.createStorage(3, 3, Direction.North);
        Storage storage5 = Storage.createStorage(4, 4, Direction.North);
        Storage storage6 = Storage.createStorage(5, 5, Direction.North);

        storageList.add(storage1);
        storageList.add(storage2);
        storageList.add(storage3);
        storageList.add(storage4);
        storageList.add(storage5);
        storageList.add(storage6);

        List<Destination> destinationList = new ArrayList<>();

        Destination destination1 = Destination.createDestination(6, 6);
        Destination destination2 = Destination.createDestination(7, 7);
        Destination destination3 = Destination.createDestination(8, 8);

        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination3);

        // When
        Mission mission = missionGenerator.generateRandomMission(3, destinationList, storageList);

        // Then

    }

}