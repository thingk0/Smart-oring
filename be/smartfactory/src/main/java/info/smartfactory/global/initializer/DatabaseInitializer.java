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
		ArrayList<? extends NodeServiceDto> nodeDtos = spaceUtil(0, 0, 2, true, StorageServiceDto.class);
		ArrayList<? extends NodeServiceDto> nodeDtos2 = spaceUtil(4, 0, 2, true, StorageServiceDto.class);
		dtos.addAll(nodeDtos);
		dtos.addAll(nodeDtos2);
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

	private void spaceUtil2(int length, boolean isVertical) {
	}
}
