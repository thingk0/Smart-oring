package info.smartfactory.domain.node.entity.type;

import java.util.ArrayList;
import java.util.List;

import info.smartfactory.domain.node.dto.request.MapAddRequest;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.ConveyorBeltType;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.constant.NodeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(name = "node_id")
@DiscriminatorValue(NodeType.CONVEYOR_BELT)
@Table(name = "conveyor_belt")
public class ConveyorBelt extends Node {

	@NotNull
	@Enumerated(EnumType.STRING)
	private ConveyorBeltType conveyorBeltType;

	private ConveyorBelt(Integer x, Integer y, EntranceDirection entranceDirection) {
		super(x, y, entranceDirection);
	}

	public static ConveyorBelt from(MapAddRequest addRequest) {
		return new ConveyorBelt(addRequest.xCoordinate(), addRequest.yCoordinate(),
			EntranceDirection.fromValue(addRequest.direction()));
	}

	public static ConveyorBelt createConveyerBelt(
		int x,
		int y,
		EntranceDirection entranceDirection,
		ConveyorBeltType conveyorBeltType
	) {
		ConveyorBelt conveyorBelt = new ConveyorBelt();
		conveyorBelt.xCoordinate = x;
		conveyorBelt.yCoordinate = y;
		conveyorBelt.entranceDirection = entranceDirection;
		conveyorBelt.conveyorBeltType = conveyorBeltType;
		return conveyorBelt;
	}

	public static List<ConveyorBelt> getFrontConveyorBelt(List<ConveyorBelt> list) {
		List<ConveyorBelt> returnList = new ArrayList<>();

		for (ConveyorBelt conveyorBelt : list) {
			if (conveyorBelt.conveyorBeltType == ConveyorBeltType.FRONT) {
				returnList.add(conveyorBelt);
			}
		}

		return returnList;
	}

	public static List<ConveyorBelt> getEndConveyorBelt(List<ConveyorBelt> list) {
		List<ConveyorBelt> returnList = new ArrayList<>();

		for (ConveyorBelt conveyorBelt : list) {
			if (conveyorBelt.conveyorBeltType == ConveyorBeltType.END) {
				returnList.add(conveyorBelt);
			}
		}

		return returnList;
	}

	@Override
	public void updateMap(String[][][] map) {
		map[getXCoordinate()][getYCoordinate()][0] = NodeType.CONVEYOR_BELT;
		map[getXCoordinate()][getYCoordinate()][1] = getEntranceDirection().name();
	}

}
