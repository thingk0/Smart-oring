package info.smartfactory.domain.dashboard.service;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AmrPercentDto {
    private Long amrId;
    private double percentage;
}
