package info.smartfactory.domain.history.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("currentAmrInfo")
public class CurrentAmrInfoRedisDto {

	@Id
	private Long amrId;

	private Long MissionId;

	private Integer battery;

	private Integer xCoordinate;

	private Integer yCoordinate;

	private LocalDateTime amrHistoryCreatedAt;
}
