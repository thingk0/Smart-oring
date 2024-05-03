package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.type.Storage;
import lombok.Data;

@Data
public class StorageServiceDto extends NodeServiceDto {

	@Override
	public Node toEntity() {
		return Storage.createStorage(xCoordinate, yCoordinate, entranceDirection);
	}

	public static StorageServiceDto from(
		Integer xCoordinate,
		Integer yCoordinate,
		EntranceDirection entranceDirection
	) {
		StorageServiceDto storageServiceDto = new StorageServiceDto();
		storageServiceDto.setXCoordinate(xCoordinate);
		storageServiceDto.setYCoordinate(yCoordinate);
		storageServiceDto.setEntranceDirection(entranceDirection);
		return storageServiceDto;
	}
}