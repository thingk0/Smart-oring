package info.smartfactory.domain.node.dto;

import lombok.*;

import java.util.List;

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
