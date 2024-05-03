package info.smartfactory.domain.node.service.command;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import lombok.Data;

@Data
public abstract class NodeDto {
	Integer xCoordinate;
	Integer yCoordinate;
	EntranceDirection entranceDirection;

	public abstract Node toEntity();
}
