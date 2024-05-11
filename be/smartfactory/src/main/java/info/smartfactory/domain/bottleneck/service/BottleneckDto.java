package info.smartfactory.domain.bottleneck.service;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottleneckDto {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Long missionId;
    private Long bottleneckPeriod;
    private LocalDateTime bottleneckCreatedAt;

}
