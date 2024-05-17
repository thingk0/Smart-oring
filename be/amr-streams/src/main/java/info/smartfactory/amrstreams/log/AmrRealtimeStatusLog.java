package info.smartfactory.amrstreams.log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import info.smartfactory.amrstreams.log.constant.AmrStatus;

public record AmrRealtimeStatusLog(
	Long missionId,
	Long amrId,
	Integer battery,
	Integer xCoordinate,
	Integer yCoordinate,
	List<Integer[]> amrRoute,
	AmrStatus amrStatus,
	LocalDateTime amrHistoryCreatedAt,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
	Instant amrHistoryCreatedAInstant,

	List<Integer[]> routeVisitedForMission,
	List<Integer[]> routeRemainingForMission,
	Integer currentStopDuration,

	Boolean hasStuff

) {

	public LocalDateTime amrHistoryCreatedAt() {
		Instant instant = amrHistoryCreatedAInstant();
		if (instant == null) {
			return null;
		}
		return LocalDateTime.ofInstant(instant, OffsetDateTime.now().toZonedDateTime().getZone());
	}
}