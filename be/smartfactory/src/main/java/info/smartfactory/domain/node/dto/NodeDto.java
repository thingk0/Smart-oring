package info.smartfactory.domain.node.dto;

import lombok.Data;

@Data
public abstract class NodeDto {

    private String direction;
    private int[] start;
    private int[] end;

    public NodeDto(String direction, int[] start, int[] end) {
        this.direction = direction;
        this.start = start;
        this.end = end;
    }
}