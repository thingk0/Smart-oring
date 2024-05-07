package info.smartfactory.domain.history.repository;

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

	private List<String> subMissions;

	private List<Integer[]> amrRoute;

	private Integer battery;

	private String amrStatus;

	private Integer xCoordinate;

	private Integer yCoordinate;

	private LocalDateTime amrHistoryCreatedAt;

}
