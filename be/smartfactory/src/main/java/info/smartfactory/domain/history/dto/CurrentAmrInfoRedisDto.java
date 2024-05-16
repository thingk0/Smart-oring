package info.smartfactory.domain.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("currentAmrInfo")
public class CurrentAmrInfoRedisDto {

    @Id
    private Long amrId;

    private Long missionId;

    private String amrRouteJson;

    private Integer battery;

    private AmrStatus amrStatus;

    private Integer xCoordinate;

    private Integer yCoordinate;

    private Integer currentStopDuration;

    private String routeRemainingForMissionJson;

    private String routeVisitedForMissionJson;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime amrHistoryCreatedAt;

}
