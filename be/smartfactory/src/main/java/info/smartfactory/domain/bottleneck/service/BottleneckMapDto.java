package info.smartfactory.domain.bottleneck.service;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottleneckMapDto {

    private Integer name;
    private List<HeatmapDto> data;

}
