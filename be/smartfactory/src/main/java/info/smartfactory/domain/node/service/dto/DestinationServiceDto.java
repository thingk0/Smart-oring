package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.Destination;
import lombok.Data;

@Data
public class DestinationServiceDto extends NodeServiceDto {

    @Override
    public Node toEntity() {
        return Destination.createDestination(xCoordinate, yCoordinate, entranceDirection);
    }

    public static DestinationServiceDto from(
        Destination destination
    ) {
        DestinationServiceDto destinationServiceDto = new DestinationServiceDto();
        destinationServiceDto.setXCoordinate(destination.getXCoordinate());
        destinationServiceDto.setYCoordinate(destination.getYCoordinate());
        destinationServiceDto.setEntranceDirection(destination.getEntranceDirection());
        destinationServiceDto.nodeType = NodeType.DESTINATION;

        return destinationServiceDto;
    }
}