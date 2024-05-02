package info.smartfactory.domain.history.dto;

import java.time.LocalDateTime;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.mission.entity.Mission;

/**
 * DTO for {@link AmrHistory}
 */
public record AmrHistoryLog(
	Long missionId,
	Long amrId,
	Integer battery,
	Integer xCoordinate,
	Integer yCoordinate,
	LocalDateTime amrHistoryCreatedAt) {

	public AmrHistory toAmrHistory(
		Mission mission,
		Amr amr
	) {
		return AmrHistory.of(
			mission,
			amr,
			battery,
			xCoordinate,
			yCoordinate
		);
	}
}