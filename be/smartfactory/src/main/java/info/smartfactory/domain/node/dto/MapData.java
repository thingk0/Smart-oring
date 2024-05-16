package info.smartfactory.domain.node.dto;

import java.util.List;

public record MapData(List<ChargerDto> charger,
                      List<DestinationDto> destination,
                      List<StorageDto> storage,
                      List<ConveyorDto> conveyorBelt) {

}