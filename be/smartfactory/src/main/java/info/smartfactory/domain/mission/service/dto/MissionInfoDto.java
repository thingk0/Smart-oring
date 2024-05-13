package info.smartfactory.domain.mission.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MissionInfoDto(Integer totalExecutionTime,
                             Integer processingTime,
                             Integer bottleneckTime,
                             Integer chargingTime,
                             Integer errorTime,
                             Integer dischargingTime,
                             List<AmrStatus> missionInfos) {

}
