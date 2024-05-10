package info.smartfactory.domain.mission.service.dto;

import info.smartfactory.domain.mission.entity.Mission;

import java.time.LocalDateTime;

/**
 * DTO for {@link Mission}
 */
public record MissionKafkaDto(Long id, LocalDateTime missionStartedAt, LocalDateTime missionFinishedAt,
                              LocalDateTime missionEstimatedTime, String fillPath) {

}