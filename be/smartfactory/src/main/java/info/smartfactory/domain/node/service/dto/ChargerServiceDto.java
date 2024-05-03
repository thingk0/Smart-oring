package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.Charger;
import lombok.Data;

@Data
public class ChargerServiceDto extends NodeServiceDto {

	@Override
	public Charger toEntity() {
		return Charger.createCharger(xCoordinate, yCoordinate, entranceDirection);
	}

	public static ChargerServiceDto from(
		Charger charger
	) {
		ChargerServiceDto chargerServiceDto = new ChargerServiceDto();
		chargerServiceDto.setXCoordinate(charger.getXCoordinate());
		chargerServiceDto.setYCoordinate(charger.getYCoordinate());
		chargerServiceDto.setEntranceDirection(charger.getEntranceDirection());
		chargerServiceDto.nodeType = NodeType.CHARGER;

		return chargerServiceDto;
	}
}