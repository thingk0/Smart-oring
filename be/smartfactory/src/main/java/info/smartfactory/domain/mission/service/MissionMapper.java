package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.NodeDto;
import info.smartfactory.domain.node.entity.Node;
import org.json.JSONArray;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    @Mapping(source = "fullPath", target = "fullPath", qualifiedByName = "fullPathForMission")
    MissionDto toDto(Mission mission);

    @Mapping(source = "XCoordinate", target = "xCoordinate")
    @Mapping(source = "YCoordinate", target = "yCoordinate")
    NodeDto toDtoNode(Node node);

    @Named("fullPathForMission")
    default List<Integer[]> fullPathForMission(String json) {
        if(json == null) return null;

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
