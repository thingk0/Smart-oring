package info.smartfactory.domain.bottleneck.service;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottleneckMapDto {

    private long bottleneckNum;
    private List<BottleneckDto> bottleneckList;

}
