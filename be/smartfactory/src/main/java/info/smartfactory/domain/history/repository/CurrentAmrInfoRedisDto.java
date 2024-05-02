package info.smartfactory.domain.history.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash
@NoArgsConstructor
public class CurrentAmrInfoRedisDto {

	@Id
	Long amrId;
	Integer battery;
	Integer xCoordinate;
	Integer yCoordinate;
}
