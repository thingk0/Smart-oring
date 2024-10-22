package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentAmrMapper {

    CurrentAmrMapper INSTANCE = Mappers.getMapper(CurrentAmrMapper.class);

    @Mapping(source = "XCoordinate", target = "xCoordinate")
    @Mapping(source = "YCoordinate", target = "yCoordinate")
    CurrentAmrInfoRedisDto mapToRedisDto(RealtimeAmrDto realtimeAmrDto);

    @AfterMapping
    default void setAmrRoute(RealtimeAmrDto source, @MappingTarget CurrentAmrInfoRedisDto target, String amrRoute) {
        target.setAmrRouteJson(amrRoute);
    }

    @AfterMapping
    default void setAmrRoutes(RealtimeAmrDto source, @MappingTarget CurrentAmrInfoRedisDto target, String amrRoute, String remainingAmrRoute, String visitedAmrRoute) {
        target.setAmrRouteJson(amrRoute);
        target.setRouteRemainingForMissionJson(remainingAmrRoute);
        target.setRouteVisitedForMissionJson(visitedAmrRoute);
    }
}