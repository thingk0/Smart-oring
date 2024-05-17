package info.smartfactory.domain.history.service;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import info.smartfactory.domain.history.entity.constant.AmrStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeAmrDto {

    private Long amrId;

    private Long missionId;

    private List<Integer[]> amrRoute;

    private Integer battery;

    private AmrStatus amrStatus;

    private Integer xCoordinate;

    private Integer yCoordinate;

    private Integer currentStopDuration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime amrHistoryCreatedAt;

    private List<Integer[]> routeVisitedForMission;

    private List<Integer[]> routeRemainingForMission;

    private Boolean hasStuff;
}
