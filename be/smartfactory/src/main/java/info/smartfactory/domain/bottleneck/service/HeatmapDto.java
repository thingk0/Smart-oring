package info.smartfactory.domain.bottleneck.service;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeatmapDto {
    private Integer x; // x축 이름
    private Integer y; // 수치
}
