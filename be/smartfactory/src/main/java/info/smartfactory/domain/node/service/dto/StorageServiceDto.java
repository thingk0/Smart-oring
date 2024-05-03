package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.Storage;
import lombok.Data;

@Data
public class StorageServiceDto extends NodeServiceDto {

	@Override
	public Node toEntity() {
		return Storage.createStorage(xCoordinate, yCoordinate, entranceDirection);
	}

	public static StorageServiceDto from(
		Storage storage
	) {
		StorageServiceDto storageServiceDto = new StorageServiceDto();
		storageServiceDto.setXCoordinate(storage.getXCoordinate());
		storageServiceDto.setYCoordinate(storage.getYCoordinate());
		storageServiceDto.setEntranceDirection(storage.getEntranceDirection());
		storageServiceDto.nodeType = NodeType.STORAGE;
		return storageServiceDto;
	}
}