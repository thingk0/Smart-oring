package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.type.Destination;
import lombok.Data;

@Data
public class DestinationServiceDto extends NodeServiceDto {

	@Override
	public Node toEntity() {
		return Destination.createDestination(xCoordinate, yCoordinate, entranceDirection);
	}

	public static DestinationServiceDto from(
		Integer xCoordinate,
		Integer yCoordinate,
		EntranceDirection entranceDirection
	) {
		DestinationServiceDto destinationServiceDto = new DestinationServiceDto();
		destinationServiceDto.setXCoordinate(xCoordinate);
		destinationServiceDto.setYCoordinate(yCoordinate);
		destinationServiceDto.setEntranceDirection(entranceDirection);
		return destinationServiceDto;
	}
}