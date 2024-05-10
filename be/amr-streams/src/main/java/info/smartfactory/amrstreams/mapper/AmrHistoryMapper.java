package info.smartfactory.amrstreams.mapper;

import info.smartfactory.amrstreams.log.AmrHistoryLog;
import info.smartfactory.amrstreams.log.AmrRealtimeStatusLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AmrHistoryMapper {

    AmrHistoryMapper INSTANCE = Mappers.getMapper(AmrHistoryMapper.class);

    @Mapping(source = "missionId", target = "mission_id")
    @Mapping(source = "amrId", target = "amr_id")
    @Mapping(source = "xCoordinate", target = "x_coordinate")
    @Mapping(source = "yCoordinate", target = "y_coordinate")
    @Mapping(source = "amrStatus", target = "amr_status")
    @Mapping(source = "amrHistoryCreatedAt", target = "amr_history_created_at")
    AmrHistoryLog toHistoryLog(AmrRealtimeStatusLog amrRealtimeStatusLog);

}
