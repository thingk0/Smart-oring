package info.smartfactory.domain.node.dto.request;

import java.util.Objects;

public record MapAddRequest(Integer xCoordinate,
                            Integer yCoordinate,
                            String type,
                            Character direction) {

    public MapAddRequest {
        Objects.requireNonNull(xCoordinate, "x좌표는 필수 항목입니다.");
        Objects.requireNonNull(yCoordinate, "y좌표는 필수 항목입니다.");
        Objects.requireNonNull(type, "노드 타입은 필수 항목입니다.");
        Objects.requireNonNull(direction, "방향은 필수 항목입니다.");
    }
}

