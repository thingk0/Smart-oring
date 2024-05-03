package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.type.Charger;
import lombok.Data;

@Data
public class ChargerServiceDto extends NodeServiceDto {

	@Override
	public Charger toEntity() {
		return Charger.createCharger(xCoordinate, yCoordinate, entranceDirection);
	}

	public static ChargerServiceDto from(
		Integer xCoordinate,
		Integer yCoordinate,
		EntranceDirection entranceDirection
	) {
		ChargerServiceDto chargerServiceDto = new ChargerServiceDto();
		chargerServiceDto.setXCoordinate(xCoordinate);
		chargerServiceDto.setYCoordinate(yCoordinate);
		chargerServiceDto.setEntranceDirection(entranceDirection);
		return chargerServiceDto;
	}
}