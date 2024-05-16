package info.smartfactory.global.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
	basePackages = "info.smartfactory.domain.history.repository.live",
	redisTemplateRef = "liveRedisTemplate",
	keyValueTemplateRef = "liveRedisKeyValueTemplate"
)
public class RedisConfig {

	@Value("${spring.data.redis.password:#{null}}")
	private Optional<String> password;

	// 로봇 최신 상태 저장
	@Bean
	@Primary
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost", 6379);
		password.ifPresent(configuration::setPassword);
		return new LettuceConnectionFactory(configuration);
	}

	@Bean(name = "liveRedisTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}

	@Bean(name = "liveRedisKeyValueTemplate")
	public KeyValueTemplate liveRedisKeyValueTemplate() {
		return new RedisKeyValueTemplate(
			new RedisKeyValueAdapter(redisTemplate()),
			new RedisMappingContext()
		);
	}
}

