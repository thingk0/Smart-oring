package info.smartfactory.domain.history.dto;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link AmrHistory}
 */
public record AmrHistoryLog(Long missionId,
                            Long amrId,
                            Integer battery,
                            Integer xCoordinate,
                            Integer yCoordinate,
                            List<Integer[]> amrRoute,
                            AmrStatus amrStatus,
                            LocalDateTime amrHistoryCreatedAt) {

}