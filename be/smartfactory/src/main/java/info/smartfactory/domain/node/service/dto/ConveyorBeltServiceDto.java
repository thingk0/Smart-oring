package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.constant.ConveyorBeltType;
import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.ConveyorBelt;
import lombok.Data;

@Data
public class ConveyorBeltServiceDto extends NodeServiceDto {

	ConveyorBeltType conveyorBeltType;

	public static ConveyorBeltServiceDto from(
		ConveyorBelt conveyorBelt
	) {
		ConveyorBeltServiceDto chargerServiceDto = new ConveyorBeltServiceDto();
		chargerServiceDto.setXCoordinate(conveyorBelt.getXCoordinate());
		chargerServiceDto.setYCoordinate(conveyorBelt.getYCoordinate());
		chargerServiceDto.setEntranceDirection(conveyorBelt.getEntranceDirection());
		chargerServiceDto.setConveyorBeltType(conveyorBelt.getConveyorBeltType());
		chargerServiceDto.nodeType = NodeType.CONVEYOR_BELT;

		return chargerServiceDto;
	}

	@Override
	public ConveyorBelt toEntity() {
		return ConveyorBelt.createConveyerBelt(xCoordinate, yCoordinate, entranceDirection, conveyorBeltType);
	}
}