package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.ConveyerBelt;
import lombok.Data;

@Data
public class ConveyorBeltServiceDto extends NodeServiceDto {

	public static ConveyorBeltServiceDto from(
		ConveyerBelt conveyerBelt
	) {
		ConveyorBeltServiceDto chargerServiceDto = new ConveyorBeltServiceDto();
		chargerServiceDto.setXCoordinate(conveyerBelt.getXCoordinate());
		chargerServiceDto.setYCoordinate(conveyerBelt.getYCoordinate());
		chargerServiceDto.setEntranceDirection(conveyerBelt.getEntranceDirection());
		chargerServiceDto.nodeType = NodeType.CONVEYER_BELT;

		return chargerServiceDto;
	}

	@Override
	public ConveyerBelt toEntity() {
		return ConveyerBelt.createConveyerBelt(xCoordinate, yCoordinate, entranceDirection);
	}
}