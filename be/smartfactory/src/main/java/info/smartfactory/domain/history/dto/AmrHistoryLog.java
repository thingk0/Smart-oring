package info.smartfactory.domain.history.dto;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.mission.entity.Mission;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link AmrHistory}
 */
public record AmrHistoryLog(
        Long missionId,
        Long amrId,
        Integer battery,
        Integer xCoordinate,
        Integer yCoordinate,
        List<Integer[]> amrRoute,
        String amrStatus,
        LocalDateTime amrHistoryCreatedAt
        ) {

}