package info.smartfactory.domain.bottleneck.service;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BottleneckMapDto {
    private long bottleneckNum;
    private List<BottleneckDto> bottleneckList;
}
