package info.smartfactory.domain.bottleneck.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddBottleneckRequest {
    @NotNull(message = "x좌표는 필수 항목입니다.")
    int x_coordinate;

    @NotNull(message = "y좌표는 필수 항목입니다.")
    int y_coordinate;

    int missionId;

    LocalDateTime bottleneckCreatedAt;
}