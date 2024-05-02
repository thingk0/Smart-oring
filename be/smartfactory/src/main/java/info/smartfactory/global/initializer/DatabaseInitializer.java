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
import info.smartfactory.domain.node.service.command.ChargerDto;
import info.smartfactory.domain.node.service.command.DestinationDto;
import info.smartfactory.domain.node.service.command.NodeDto;
import info.smartfactory.domain.node.service.command.StorageDto;
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
		ArrayList<NodeDto> dtos = new ArrayList<>();
		ArrayList<? extends NodeDto> nodeDtos = spaceUtil(0, 0, 2, true, StorageDto.class);
		ArrayList<? extends NodeDto> nodeDtos2 = spaceUtil(4, 0, 2, true, StorageDto.class);
		dtos.addAll(nodeDtos);
		dtos.addAll(nodeDtos2);
		nodeService.addNodes(dtos);
	}

	private <T extends NodeDto> ArrayList<? extends NodeDto> spaceUtil(
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

	private <T extends NodeDto> void setDirection(boolean isVertical, T e, T e2) {
		HashMap<Class<? extends NodeDto>, Function<List<T>, Void>> directionMap = new HashMap<>();
		EntranceDirection[] directions = {EntranceDirection.WEST, EntranceDirection.EAST, EntranceDirection.NORTH,
			EntranceDirection.SOUTH};
		int idx = isVertical ? 0 : 2;

		directionMap.put(StorageDto.class, (List<T> dtos) -> {
			((StorageDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((StorageDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
			return null;
		});
		directionMap.put(ChargerDto.class, (List<T> dtos) -> {
			((ChargerDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((ChargerDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
			return null;
		});
		directionMap.put(DestinationDto.class, (List<T> dtos) -> {
			((DestinationDto)dtos.getFirst()).setEntranceDirection(directions[idx]);
			((DestinationDto)dtos.get(1)).setEntranceDirection(directions[idx + 1]);
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
