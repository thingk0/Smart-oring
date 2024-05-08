package info.smartfactory.domain.bottleneck.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BottleneckDto {
    int xCoordinate;

    int yCoordinate;

    Long missionId;

    Long bottleneckPeriod;

    LocalDateTime bottleneckCreatedAt;
}
