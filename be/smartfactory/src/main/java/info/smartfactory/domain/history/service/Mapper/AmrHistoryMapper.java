package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.service.dto.AmrHistoryDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AmrHistoryMapper {
    AmrHistoryDto toDto(AmrHistory amrHistory);

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
