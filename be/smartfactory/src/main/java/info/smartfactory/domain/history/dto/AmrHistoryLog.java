package info.smartfactory.domain.history.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.entity.constant.AmrStatus;

/**
 * DTO for {@link AmrHistory}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AmrHistoryLog(Long missionId,
                            Long amrId,
                            Integer battery,
                            Integer xCoordinate,
                            Integer yCoordinate,
                            List<Integer[]> amrRoute,
                            AmrStatus amrStatus,
                            LocalDateTime amrHistoryCreatedAt) {

}