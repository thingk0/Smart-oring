package info.smartfactory.domain.mission.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link info.smartfactory.domain.node.entity.Node}
 */
@Value
public class NodeDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;
    Long id;
    Integer xCoordinate;
    Integer yCoordinate;
    EntranceDirection entranceDirection;
}