package info.smartfactory.domain.node.entity.constant;

import lombok.Getter;

@Getter
public enum EntranceDirection {

    EAST('E'), // 동
    WEST('W'), // 서
    SOUTH('S'), // 남
    NORTH('N'); // 북

    private final char value;

    EntranceDirection(char value) {
        this.value = value;
    }

    public static EntranceDirection fromValue(char value) {
        for (EntranceDirection direction : EntranceDirection.values()) {
            if (direction.getValue() == value) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
