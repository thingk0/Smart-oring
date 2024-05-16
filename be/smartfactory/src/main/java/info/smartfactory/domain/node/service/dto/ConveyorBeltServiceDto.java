package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.ConveyorBelt;
import lombok.Data;

@Data
public class ConveyorBeltServiceDto extends NodeServiceDto {

	Boolean isInteractive = false;

	public static ConveyorBeltServiceDto from(
		ConveyorBelt conveyorBelt
	) {
		ConveyorBeltServiceDto chargerServiceDto = new ConveyorBeltServiceDto();
		chargerServiceDto.setXCoordinate(conveyorBelt.getXCoordinate());
		chargerServiceDto.setYCoordinate(conveyorBelt.getYCoordinate());
		chargerServiceDto.setEntranceDirection(conveyorBelt.getEntranceDirection());
		chargerServiceDto.nodeType = NodeType.CONVEYOR_BELT;

		return chargerServiceDto;
	}

	@Override
	public ConveyorBelt toEntity() {
		return ConveyorBelt.createConveyerBelt(xCoordinate, yCoordinate, entranceDirection, isInteractive);
	}
}