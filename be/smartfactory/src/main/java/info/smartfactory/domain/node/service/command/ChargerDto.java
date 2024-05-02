package info.smartfactory.domain.node.service.command;

import info.smartfactory.domain.node.entity.type.Charger;
import lombok.Data;

@Data
public class ChargerDto extends NodeDto {

	@Override
	public Charger toEntity() {
		return Charger.createCharger(xCoordinate, yCoordinate, entranceDirection);
	}
}