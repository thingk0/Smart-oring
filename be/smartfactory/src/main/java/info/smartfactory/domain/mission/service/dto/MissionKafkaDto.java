package info.smartfactory.domain.mission.service.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

/**
 * DTO for {@link Mission}
 */

public record MissionKafkaDto(
	Long id,
	LocalDateTime missionStartedAt,
	LocalDateTime missionFinishedAt,
	Integer missionEstimatedTime,
	List<Integer[]> fullPath,
	Long amrId
) {

}