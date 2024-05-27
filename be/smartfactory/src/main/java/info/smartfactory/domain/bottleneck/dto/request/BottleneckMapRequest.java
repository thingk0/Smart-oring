package info.smartfactory.domain.bottleneck.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BottleneckMapRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String missionType;
}
