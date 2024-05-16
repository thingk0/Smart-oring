package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CurrentToRealAmrMapper {
    CurrentToRealAmrMapper INSTANCE = Mappers.getMapper(CurrentToRealAmrMapper.class);

    @Mapping(source = "XCoordinate", target = "xCoordinate")
    @Mapping(source = "YCoordinate", target = "yCoordinate")
    RealtimeAmrDto mapToRedisDto(CurrentAmrInfoRedisDto currentAmrInfoRedisDto);

    @AfterMapping
    default void setAmrRoute(CurrentAmrInfoRedisDto currentAmrInfoRedisDto, @MappingTarget RealtimeAmrDto target, List<Integer[]> amrRoute) {
        target.setAmrRoute(amrRoute);
    }
}
