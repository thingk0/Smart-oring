package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AmrHistoryMapper {
    AmrHistoryMapper INSTANCE = Mappers.getMapper(AmrHistoryMapper.class);

    // AmrHistoryLog -> CurrentAmrInfoRedisDto 매핑
    BatchAmrInfoRedisDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

    @AfterMapping
    default void setStopPeriod(AmrHistoryLog source, @MappingTarget BatchAmrInfoRedisDto target, long stopPeriod) {
        target.setStopPeriod(stopPeriod);
    }

}
