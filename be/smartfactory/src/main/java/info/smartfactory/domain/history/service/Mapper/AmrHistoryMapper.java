package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.dto.BatchAmrInfoRedisDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AmrHistoryMapper {

    AmrHistoryMapper INSTANCE = Mappers.getMapper(AmrHistoryMapper.class);

    // AmrHistoryLog -> CurrentAmrInfoRedisDto 매핑
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stopPeriod", ignore = true)
    BatchAmrInfoRedisDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

    @AfterMapping
    default void setStopPeriod(AmrHistoryLog source, @MappingTarget BatchAmrInfoRedisDto target, long stopPeriod) {
        target.setStopPeriod(stopPeriod);
    }

}
