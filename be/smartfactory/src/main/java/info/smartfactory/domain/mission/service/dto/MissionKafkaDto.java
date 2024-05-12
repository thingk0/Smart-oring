package info.smartfactory.domain.mission.service.dto;

import java.time.LocalDateTime;

import info.smartfactory.domain.mission.entity.Mission;

/**
 * DTO for {@link Mission}
 */
public record MissionKafkaDto(
	Long id,
	LocalDateTime missionStartedAt,
	LocalDateTime missionFinishedAt,
	Integer missionEstimatedTime,
	String fullPath
) {

}