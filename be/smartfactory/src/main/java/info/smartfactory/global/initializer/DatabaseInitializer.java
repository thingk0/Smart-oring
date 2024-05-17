package info.smartfactory.global.initializer;

import static info.smartfactory.domain.node.entity.constant.EntranceDirection.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import info.smartfactory.domain.node.entity.constant.ConveyorBeltType;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.domain.node.service.dto.ChargerServiceDto;
import info.smartfactory.domain.node.service.dto.ConveyorBeltServiceDto;
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

		dtos.add(createNode(0, 14, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 16, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 18, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 20, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 22, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 24, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 26, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 28, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 30, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 32, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 34, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 36, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 38, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 40, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 42, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 44, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 46, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 48, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 50, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 52, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 54, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 56, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 58, SOUTH, ChargerServiceDto.class));
		dtos.add(createNode(0, 60, SOUTH, ChargerServiceDto.class));

		dtos.addAll(createStorageServiceDto(6, 5, 18));
		dtos.addAll(createStorageServiceDto(14, 5, 18));
		dtos.addAll(createStorageServiceDto(22, 5, 18));
		dtos.addAll(createStorageServiceDto(30, 5, 18));

		dtos.addAll(createStorageServiceDto(6, 28, 18));
		dtos.addAll(createStorageServiceDto(14, 28, 18));
		dtos.addAll(createStorageServiceDto(22, 28, 18));
		dtos.addAll(createStorageServiceDto(30, 28, 18));

		dtos.add(createNode(6, 67, WEST, DestinationServiceDto.class));
		dtos.add(createNode(7, 67, WEST, DestinationServiceDto.class));
		dtos.add(createNode(8, 67, WEST, DestinationServiceDto.class));

		dtos.add(createNode(30, 67, WEST, DestinationServiceDto.class));
		dtos.add(createNode(31, 67, WEST, DestinationServiceDto.class));
		dtos.add(createNode(32, 67, WEST, DestinationServiceDto.class));

		dtos.add(createConveyorBeltDto(6, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(6, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(6, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(7, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(7, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(7, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(8, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(8, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(8, 59, WEST, ConveyorBeltType.END));

		dtos.add(createConveyorBeltDto(14, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(14, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(14, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(15, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(15, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(15, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(16, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(16, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(16, 59, WEST, ConveyorBeltType.END));

		dtos.add(createConveyorBeltDto(22, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(22, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(22, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(23, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(23, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(23, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(24, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(24, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(24, 59, WEST, ConveyorBeltType.END));

		dtos.add(createConveyorBeltDto(30, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(30, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(30, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(31, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(31, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(31, 59, WEST, ConveyorBeltType.END));
		dtos.add(createConveyorBeltDto(32, 52, WEST, ConveyorBeltType.FRONT));
		dtos.add(createConveyorBeltDto(32, 53, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 54, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 55, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 56, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 57, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 58, WEST, ConveyorBeltType.MIDDLE));
		dtos.add(createConveyorBeltDto(32, 59, WEST, ConveyorBeltType.END));

		nodeService.addNodes(dtos);
	}

	ConveyorBeltServiceDto createConveyorBeltDto(int x, int y, EntranceDirection direction, ConveyorBeltType type) {
		ConveyorBeltServiceDto conveyorBeltServiceDto = new ConveyorBeltServiceDto();

		conveyorBeltServiceDto.setEntranceDirection(direction);
		conveyorBeltServiceDto.setXCoordinate(x);
		conveyorBeltServiceDto.setYCoordinate(y);

		conveyorBeltServiceDto.setConveyorBeltType(type);

		return conveyorBeltServiceDto;
	}

	List<StorageServiceDto> createStorageServiceDto(int x, int y, int length) {
		List<StorageServiceDto> storageServiceDtos = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			StorageServiceDto storageServiceDto = new StorageServiceDto();

			storageServiceDto.setEntranceDirection(NORTH);
			storageServiceDto.setXCoordinate(x);
			storageServiceDto.setYCoordinate(y + i);
			storageServiceDtos.add(storageServiceDto);
		}
		for (int i = 0; i < length; i++) {
			StorageServiceDto storageServiceDto = new StorageServiceDto();

			storageServiceDto.setEntranceDirection(SOUTH);
			storageServiceDto.setXCoordinate(x + 1);
			storageServiceDto.setYCoordinate(y + i);
			storageServiceDtos.add(storageServiceDto);
		}

		return storageServiceDtos;
	}

	<T extends NodeServiceDto> NodeServiceDto createNode(int x, int y, EntranceDirection direction, Class<T> clazz) {

		NodeServiceDto dto;

		switch (clazz.getSimpleName()) {
			case "ChargerServiceDto" -> dto = new ChargerServiceDto();
			case "StorageServiceDto" -> dto = new StorageServiceDto();
			case "DestinationServiceDto" -> dto = new DestinationServiceDto();
			case "ConveyorBeltServiceDto" -> dto = new ConveyorBeltServiceDto();
			default -> throw new IllegalStateException("Unexpected value: " + clazz.getSimpleName());
		}
		dto.setEntranceDirection(direction);
		dto.setXCoordinate(x);
		dto.setYCoordinate(y);

		return dto;
	}

}
