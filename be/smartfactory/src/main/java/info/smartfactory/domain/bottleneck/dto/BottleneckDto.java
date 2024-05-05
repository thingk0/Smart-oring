package info.smartfactory.domain.bottleneck.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BottleneckDto {
    int x_coordinate;

    int y_coordinate;

    Long mission_id;

    LocalDateTime bottleneck_time;

}
