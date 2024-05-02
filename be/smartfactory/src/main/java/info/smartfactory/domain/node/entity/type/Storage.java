package info.smartfactory.domain.node.entity.type;

import info.smartfactory.domain.node.dto.request.MapAddRequest;
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
@DiscriminatorValue(NodeType.STORAGE)
@Table(name = "storage")
public class Storage extends Node {

    private Storage(Integer x, Integer y, EntranceDirection entranceDirection) {
        super(x, y, entranceDirection);
    }

    public static Storage from(MapAddRequest addRequest) {
        return new Storage(addRequest.xCoordinate(), addRequest.yCoordinate(),
                           EntranceDirection.fromValue(addRequest.direction()));
    }

    @Override
    public void updateMap(String[][][] map) {
        map[getXCoordinate()][getYCoordinate()][0] = NodeType.STORAGE;
        map[getXCoordinate()][getYCoordinate()][1] = getEntranceDirection().name();
    }

}