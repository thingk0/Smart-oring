package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RealtimeAmrMapper {

    RealtimeAmrMapper INSTANCE = Mappers.getMapper(RealtimeAmrMapper.class);

    RealtimeAmrDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

    @AfterMapping
    default void setStopPeriod(AmrHistoryLog source, @MappingTarget RealtimeAmrDto target, long stopPeriod) {
        target.setStopPeriod(stopPeriod);
    }
}
