package info.smartfactory.domain.history.service;

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
public class RealtimeAmrDto {

    private Long amrId;

    private Long missionId;

    private List<Integer[]> amrRoute;

    private Integer battery;

    private AmrStatus amrStatus;

    private Integer xCoordinate;

    private Integer yCoordinate;

    private long stopPeriod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime amrHistoryCreatedAt;

}
