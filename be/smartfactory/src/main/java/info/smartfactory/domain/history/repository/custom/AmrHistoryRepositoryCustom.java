package info.smartfactory.domain.history.repository.custom;

import info.smartfactory.domain.mission.service.dto.MissionInfoDto;

public interface AmrHistoryRepositoryCustom {

    MissionInfoDto fetchMissionAnalysisInfo(Long missionId);
}
