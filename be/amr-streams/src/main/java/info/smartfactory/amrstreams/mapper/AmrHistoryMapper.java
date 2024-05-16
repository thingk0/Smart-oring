package info.smartfactory.amrstreams.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.smartfactory.amrstreams.log.AmrHistoryLog;
import info.smartfactory.amrstreams.log.AmrRealtimeStatusLog;

@Mapper
public interface AmrHistoryMapper {

	AmrHistoryMapper INSTANCE = Mappers.getMapper(AmrHistoryMapper.class);

	@Mapping(source = "missionId", target = "mission_id")
	@Mapping(source = "amrId", target = "amr_id")
	@Mapping(source = "xCoordinate", target = "x_coordinate")
	@Mapping(source = "yCoordinate", target = "y_coordinate")
	@Mapping(source = "amrStatus", target = "amr_status")
	@Mapping(source = "amrHistoryCreatedAt", target = "amr_history_created_at")
	@Mapping(source = "routeVisitedForMission", target = "routeVisitedForMission", qualifiedByName = "mapRouteStringToList")
	@Mapping(source = "routeRemainingForMission", target = "routeRemainingForMission", qualifiedByName = "mapRouteStringToList")
	AmrHistoryLog toHistoryLog(AmrRealtimeStatusLog amrRealtimeStatusLog);

	@Named("mapRouteStringToList")
	default String mapRouteStringToList(List<Integer[]> route) throws JsonProcessingException {
		if (route == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(route);
	}
}
