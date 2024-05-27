package info.smartfactory.domain.node.service.dto;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import lombok.Data;

@Data
public abstract class NodeServiceDto {

    Integer xCoordinate;
    Integer yCoordinate;
    EntranceDirection entranceDirection;
    String nodeType;

    public abstract Node toEntity();
}
