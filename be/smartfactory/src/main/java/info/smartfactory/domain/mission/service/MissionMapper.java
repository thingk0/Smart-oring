package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.NodeDto;
import info.smartfactory.domain.node.entity.Node;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MissionMapper {

    MissionDto toDto(Mission mission);

    @Mapping(source = "XCoordinate", target = "xCoordinate")
    @Mapping(source = "YCoordinate", target = "yCoordinate")
    NodeDto toDtoNode(Node node);
}
