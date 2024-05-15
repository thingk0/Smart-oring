package info.smartfactory.domain.mission.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MissionAnalysisDto {

    private MissionExecutionTimeAnalysisDto missionExecutionTimeAnalysis;
    private List<AmrStatusTimeLine> amrStatusTimeline;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MissionExecutionTimeAnalysisDto {

        private String amrCode;
        private Long missionId;
        private Integer totalExecutionTime;
        private Integer processingTime;
        private Integer bottleneckTime;
        private Integer chargingTime;
        private Integer errorTime;
        private Integer dischargingTime;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AmrStatusTimeLine {

        private AmrStatus amrStatus;
        private List<Long> startToEnd;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AmrStatusWithTime {

        private AmrStatus amrStatus;
        private LocalDateTime amrHistoryCreatedAt;

    }

}
