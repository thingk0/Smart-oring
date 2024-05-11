package info.smartfactory.domain.node.entity.type;

import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import info.smartfactory.domain.node.entity.constant.NodeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(name = "node_id")
@DiscriminatorValue(NodeType.CONVEYER_BELT)
@Table(name = "conveyer_belt")
public class ConveyerBelt extends Node {

    private ConveyerBelt(Integer x, Integer y, EntranceDirection entranceDirection) {
        super(x, y, entranceDirection);
    }

    public static ConveyerBelt createConveyerBelt(int x, int y, EntranceDirection entranceDirection) {
        return new ConveyerBelt(x, y, entranceDirection);

    }

    @Override
    public void updateMap(String[][][] map) {
        map[getXCoordinate()][getYCoordinate()][0] = NodeType.STORAGE;
        map[getXCoordinate()][getYCoordinate()][1] = getEntranceDirection().name();
    }


}
