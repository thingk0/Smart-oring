package info.smartfactory.amrstreams.log;

import java.time.LocalDateTime;

import info.smartfactory.amrstreams.log.constant.AmrStatus;

public record AmrHistoryLog(
	Long mission_id,
	Long amr_id,
	Integer battery,
	Integer x_coordinate,
	Integer y_coordinate,
	AmrStatus amr_status,
	LocalDateTime amr_history_created_at,

	String routeVisitedForMission,
	String routeRemainingForMission,
	Integer currentStopDuration
) {

}