package info.smartfactory.domain.node.service.command;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.type.Storage;
import lombok.Data;

@Data
public class StorageDto extends NodeDto {

	@Override
	public Node toEntity() {
		return Storage.createStorage(xCoordinate, yCoordinate, entranceDirection);
	}
}