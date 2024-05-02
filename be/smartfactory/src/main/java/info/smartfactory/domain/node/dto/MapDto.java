package info.smartfactory.domain.node.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MapDto {

    //    {
//        "storage": [
//        {
//            "direction": 0, // 방향
//                "start": [0, 0],
//            "end": [1, 0],
//        }
//						, ...
//					],
//        "charger": [],
//        "destination": []
//    }
    private List<StorageDto> storage;
    private List<ChargerDto> charger;
    private List<DestinationDto> destination;

}
