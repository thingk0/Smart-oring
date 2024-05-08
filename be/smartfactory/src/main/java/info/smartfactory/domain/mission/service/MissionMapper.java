package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MissionMapper {

    MissionDto toDto(Mission mission);
}
