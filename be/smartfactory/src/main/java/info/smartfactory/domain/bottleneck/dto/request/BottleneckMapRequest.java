package info.smartfactory.domain.bottleneck.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BottleneckMapRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String missionType;
}
