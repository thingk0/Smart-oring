package info.smartfactory.domain.history.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.entity.constant.AmrStatus;

/**
 * DTO for {@link AmrHistory}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AmrHistoryLog(
	Long missionId,
	Long amrId,
	Integer battery,
	Integer xCoordinate,
	Integer yCoordinate,
	List<Integer[]> amrRoute,
	AmrStatus amrStatus,
	@Deprecated
	LocalDateTime amrHistoryCreatedAt,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
	Instant amrHistoryCreatedAInstant,

	List<Integer[]> routeRemainingForMission,
	List<Integer[]> routeVisitedForMission,
	Integer currentStopDuration

) {

	public LocalDateTime amrHistoryCreatedAt() {
		Instant instant = amrHistoryCreatedAInstant();
		if (instant == null) {
			return null;
		}
		return LocalDateTime.ofInstant(instant, OffsetDateTime.now().toZonedDateTime().getZone());
	}

}