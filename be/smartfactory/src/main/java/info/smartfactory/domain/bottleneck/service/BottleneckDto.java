package info.smartfactory.domain.bottleneck.service;

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
