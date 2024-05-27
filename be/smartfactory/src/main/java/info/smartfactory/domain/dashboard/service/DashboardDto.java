package info.smartfactory.domain.dashboard.service;

import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.ErrorDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DashboardDto {

    // 오늘 실시간 미션 처리량 - 시간 간격?
    private long[] todayOutputGraph;

    // 어제 시간 별 미션 처리량
    private long[] yesterdayOutputGraph;

    // 오늘 생산량
    private long todayTotalOutput;

    // 가동률
    private int totalUsagePercent;

    // amr별 사용률
    private List<AmrPercentDto> amrUsagePercent;

    // amr별 에러율
    private List<AmrPercentDto> amrErrorPercent;

    // 실시간 에러
    private List<ErrorDto> realtimeError;

    // 실시간 병목
    private List<BottleneckDto> realtimeBottleneck;

}
