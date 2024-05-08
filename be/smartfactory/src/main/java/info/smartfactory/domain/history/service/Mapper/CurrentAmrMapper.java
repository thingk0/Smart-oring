package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrentAmrMapper {
    CurrentAmrMapper INSTANCE = Mappers.getMapper(CurrentAmrMapper.class);

    // RequestDto -> MessageBodyDto 매핑
    CurrentAmrInfoRedisDto toCurrentAmrInfoDto(AmrHistoryLog amrHistoryLog);
}
