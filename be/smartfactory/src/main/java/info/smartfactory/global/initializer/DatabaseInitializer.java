package info.smartfactory.global.initializer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.domain.node.service.dto.ChargerServiceDto;
import info.smartfactory.domain.node.service.dto.DestinationServiceDto;
import info.smartfactory.domain.node.service.dto.NodeServiceDto;
import info.smartfactory.domain.node.service.dto.StorageServiceDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

	final NodeService nodeService;

	@Override
	public void run(String... args) throws Exception {
		initializeMap();
	}

	private void initializeMap() throws Exception {
		ArrayList<NodeServiceDto> dtos = new ArrayList<>();

		Object[][] nodes = new Object[][] {
			{16, 4, 11, true, StorageServiceDto.class},
			{28, 4, 7, true, StorageServiceDto.class},
			{16, 10, 5, true, StorageServiceDto.class},
			{22, 10, 5, true, StorageServiceDto.class},
			{28, 10, 7, true, StorageServiceDto.class},
			{9, 19, 11, false, StorageServiceDto.class},
			{16, 19, 11, false, StorageServiceDto.class},
			{23, 19, 11, false, StorageServiceDto.class},
			{30, 19, 11, false, StorageServiceDto.class},
			{37, 32, 11, false, StorageServiceDto.class},
			{37, 19, 11, false, StorageServiceDto.class},
			{9, 32, 11, false, StorageServiceDto.class},
			{16, 32, 11, false, StorageServiceDto.class},
			{23, 32, 11, false, StorageServiceDto.class},
			{30, 32, 11, false, StorageServiceDto.class},
			{37, 32, 11, false, StorageServiceDto.class},
			{9, 45, 11, false, StorageServiceDto.class},
			{16, 45, 11, false, StorageServiceDto.class},
			{16, 92, 5, true, StorageServiceDto.class},
			{22, 92, 5, true, StorageServiceDto.class},
			{28, 92, 7, true, StorageServiceDto.class},
			{16, 86, 11, true, StorageServiceDto.class},
			{28, 86, 7, true, StorageServiceDto.class},
			{9, 71, 11, false, StorageServiceDto.class},
			{16, 71, 11, false, StorageServiceDto.class},
			{23, 71, 11, false, StorageServiceDto.class},
			{30, 71, 11, false, StorageServiceDto.class},
			{37, 71, 11, false, StorageServiceDto.class},
			{9, 58, 11, false, StorageServiceDto.class},
			{16, 58, 11, false, StorageServiceDto.class},
			{23, 58, 11, false, StorageServiceDto.class},
			{30, 58, 11, false, StorageServiceDto.class},
			{37, 58, 11, false, StorageServiceDto.class},
			{23, 45, 11, false, StorageServiceDto.class},
			{30, 45, 11, false, StorageServiceDto.class},
			{37, 45, 11, false, StorageServiceDto.class},

			{2, 6, 11, true, ChargerServiceDto.class},
			{38, 6, 11, true, ChargerServiceDto.class},
			{2, 88, 6, false, ChargerServiceDto.class},
			{38, 88, 6, false, ChargerServiceDto.class},

			{3, 21, 4, false, DestinationServiceDto.class},
			{3, 26, 4, false, DestinationServiceDto.class},
			{3, 31, 4, false, DestinationServiceDto.class},
			{3, 36, 4, false, DestinationServiceDto.class},
			{3, 41, 4, false, DestinationServiceDto.class},
			{3, 46, 4, false, DestinationServiceDto.class},
			{44, 21, 4, false, DestinationServiceDto.class},
			{44, 26, 4, false, DestinationServiceDto.class},
			{44, 31, 4, false, DestinationServiceDto.class},
			{44, 36, 4, false, DestinationServiceDto.class},
			{44, 41, 4, false, DestinationServiceDto.class},
			{44, 46, 4, false, DestinationServiceDto.class},
			{3, 76, 4, false, DestinationServiceDto.class},
			{3, 71, 4, false, DestinationServiceDto.class},
			{3, 66, 4, false, DestinationServiceDto.class},
			{3, 61, 4, false, DestinationServiceDto.class},
			{3, 56, 4, false, DestinationServiceDto.class},
			{3, 51, 4, false, DestinationServiceDto.class},
			{44, 76, 4, false, DestinationServiceDto.class},
			{44, 71, 4, false, DestinationServiceDto.class},
			{44, 66, 4, false, DestinationServiceDto.class},
			{44, 61, 4, false, DestinationServiceDto.class},
			{44, 56, 4, false, DestinationServiceDto.class},
			{44, 51, 4, false, DestinationServiceDto.class},
		};

		for (Object[] node : nodes) {
			dtos.addAll(spaceUtil((int)node[0], (int)node[1], (int)node[2], (boolean)node[3],
				(Class<? extends NodeServiceDto>)node[4]));
		}

		nodeService.addNodes(dtos);
	}

	private <T extends NodeServiceDto> ArrayList<? extends NodeServiceDto> spaceUtil(
		int tx,
		int ty,
		int length,
		boolean isVertical,
		Class<T> classType
	) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		ArrayList<T> dtos = new ArrayList<>();

		for (int i = 0; i < length; i++) {
			T e = classType.getDeclaredConstructor().newInstance();
			T e2 = classType.getDeclaredConstructor().newInstance();
			if (isVertical) {
				e.setXCoordinate(tx + i);
				e.setYCoordinate(ty);

				e2.setXCoordinate(tx + i);
				e2.setYCoordinate(ty + 1);
			} else {
				e.setXCoordinate(ty + i);
				e.setYCoordinate(tx);

				e2.setYCoordinate(ty + i);
				e2.setXCoordinate(tx + 1);
			}
			setDirection(isVertical, e, e2);
			dtos.add(e);
			dtos.add(e2);
		}

		return dtos;
	}

	private <T extends NodeServiceDto> void setDirection(boolean isVertical, T e, T e2) {
		HashMap<Class<? extends NodeServiceDto>, Function<List<T>, Void>> directionMap = new HashMap<>();
		EntranceDirection[] directions = {EntranceDirection.WEST, EntranceDirection.EAST, EntranceDirection.NORTH,
			EntranceDirection.SOUTH};
		int idx = isVertical ? 0 : 2;

		directionMap.put(StorageServiceDto.class, (List<T> dtos) -> {
			((StorageServiceDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((StorageServiceDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
			return null;
		});
		directionMap.put(ChargerServiceDto.class, (List<T> dtos) -> {
			((ChargerServiceDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((ChargerServiceDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
			return null;
		});
		directionMap.put(DestinationServiceDto.class, (List<T> dtos) -> {
			((DestinationServiceDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((DestinationServiceDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
			return null;
		});
		ArrayList<T> t = new ArrayList<>();
		t.add(e);
		t.add(e2);
		directionMap.get(e.getClass()).apply(t);
	}

}
