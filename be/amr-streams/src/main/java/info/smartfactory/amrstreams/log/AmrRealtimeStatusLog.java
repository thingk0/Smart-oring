package info.smartfactory.amrstreams.log;

import info.smartfactory.amrstreams.log.constant.AmrStatus;
import java.time.LocalDateTime;
import java.util.List;

public record AmrRealtimeStatusLog(Long missionId,
                                   Long amrId,
                                   Integer battery,
                                   Integer xCoordinate,
                                   Integer yCoordinate,
                                   List<Integer[]> amrRoute,
                                   AmrStatus amrStatus,
                                   LocalDateTime amrHistoryCreatedAt) {

}