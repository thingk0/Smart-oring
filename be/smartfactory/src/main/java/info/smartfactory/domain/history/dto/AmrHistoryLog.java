package info.smartfactory.domain.history.dto;

import info.smartfactory.domain.history.entity.AmrHistory;
import java.time.LocalDateTime;

/**
 * DTO for {@link AmrHistory}
 */
public record AmrHistoryLog(Long missionId,
                            Long amrId,
                            Integer battery,
                            Integer xCoordinate,
                            Integer yCoordinate,
                            LocalDateTime amrHistoryCreatedAt) {
}