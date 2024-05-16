package info.smartfactory.domain.history.service.Mapper;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.service.dto.AmrInfoDto;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import org.json.JSONArray;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RealtimeAmrMapper {

    RealtimeAmrMapper INSTANCE = Mappers.getMapper(RealtimeAmrMapper.class);

    RealtimeAmrDto mapToRedisDto(AmrHistoryLog amrHistoryLog);

//    @AfterMapping
//    default void setStopPeriod(AmrHistoryLog source, @MappingTarget RealtimeAmrDto target, long stopPeriod) {
//        target.setStopPeriod(stopPeriod);
//    }

    @Mapping(source = "XCoordinate", target = "xCoordinate")
    @Mapping(source = "YCoordinate", target = "yCoordinate")
    @Mapping(source = "routeVisitedForMission", target = "routeVisitedForMission", qualifiedByName = "routeVisitedForMission")
    RealtimeAmrDto toDto(AmrHistory amrHistory);

    AmrInfoDto toAmrDto(Amr amr);

    MissionDto toMissionDto(Mission mission);

    @Named("routeVisitedForMission")
    default List<Integer[]> routeVisitedForMission(String routeVisitedForMission) {
        return parseJsonStringToList(routeVisitedForMission);
    }


    default List<Integer[]> parseJsonStringToList(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<Integer[]> resultList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray innerJsonArray = jsonArray.getJSONArray(i);
            Integer[] array = new Integer[innerJsonArray.length()];

            for (int j = 0; j < innerJsonArray.length(); j++) {
                array[j] = innerJsonArray.getInt(j);
            }

            resultList.add(array);
        }
        return resultList;
    }
}
