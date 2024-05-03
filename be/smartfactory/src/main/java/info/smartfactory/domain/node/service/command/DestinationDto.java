package info.smartfactory.domain.node.service.command;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.type.Destination;
import lombok.Data;

@Data
public class DestinationDto extends NodeDto {

	@Override
	public Node toEntity() {
		return Destination.createDestination(xCoordinate, yCoordinate, entranceDirection);
	}
}