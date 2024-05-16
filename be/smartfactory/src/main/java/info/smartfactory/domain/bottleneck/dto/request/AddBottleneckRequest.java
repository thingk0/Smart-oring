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
    private int xCoordinate;

    @NotNull(message = "y좌표는 필수 항목입니다.")
    private int yCoordinate;

    private Long missionId;

    private Integer bottleneckPeriod;

    private LocalDateTime bottleneckTime;

}