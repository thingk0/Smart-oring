package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentAmrMapper {

    CurrentAmrMapper INSTANCE = Mappers.getMapper(CurrentAmrMapper.class);

    @Mapping(source = "amrId", target = "amrId")
    @Mapping(source = "missionId", target = "missionId")
    @Mapping(source = "amrRoute", target = "amrRoute")
    @Mapping(source = "battery", target = "battery")
    @Mapping(source = "amrStatus", target = "amrStatus")
    @Mapping(source = "xCoordinate", target = "xCoordinate")
    @Mapping(source = "yCoordinate", target = "yCoordinate")
    @Mapping(source = "amrHistoryCreatedAt", target = "amrHistoryCreatedAt")
    CurrentAmrInfoRedisDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

    @AfterMapping
    default void setStopPeriod(AmrHistoryLog source, @MappingTarget CurrentAmrInfoRedisDto target, long stopPeriod) {
        target.setStopPeriod(stopPeriod);
    }
}