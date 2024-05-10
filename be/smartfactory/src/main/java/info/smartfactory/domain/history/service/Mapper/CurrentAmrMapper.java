package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentAmrMapper {

    CurrentAmrMapper INSTANCE = Mappers.getMapper(CurrentAmrMapper.class);

    // AmrHistoryLog -> CurrentAmrInfoRedisDto 매핑
    CurrentAmrInfoRedisDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

    @AfterMapping
    default void setStopPeriod(AmrHistoryLog source, @MappingTarget CurrentAmrInfoRedisDto target, long stopPeriod) {
        target.setStopPeriod(stopPeriod);
    }
}