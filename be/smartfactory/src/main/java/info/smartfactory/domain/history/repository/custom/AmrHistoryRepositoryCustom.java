package info.smartfactory.domain.history.repository.custom;

import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.AmrStatusWithTime;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.MissionExecutionTimeAnalysisDto;
import java.util.List;

public interface AmrHistoryRepositoryCustom {

    MissionExecutionTimeAnalysisDto fetchMissionAnalysisInfo(Long missionId);

    List<AmrStatusWithTime> fetchAmrStatusWithTime(Long missionId);

}
