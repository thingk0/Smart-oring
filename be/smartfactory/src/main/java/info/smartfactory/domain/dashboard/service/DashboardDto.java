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
    private long [] todayMissionCount;

    // 어제 시간 별 미션 처리량
    private long [] yesterdayMissionCount;

    // 오늘 처리량
    private long todayTotalMissionCount;

    // 가동률
    private int totalUsagePercent;

    // amr별 사용률
    private Double [] amrUsagePercent;

    // amr별 에러율
    private Double [] amrErrorPercent;

    // 실시간 에러
    private List<ErrorDto> realtimeError;

    // 실시간 병목
    private List<BottleneckDto> realtimeBottleneck;
}
