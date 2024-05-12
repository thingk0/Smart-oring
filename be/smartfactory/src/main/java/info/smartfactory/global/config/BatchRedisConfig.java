package info.smartfactory.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	basePackages = "info.smartfactory.domain.history.repository.batch",
	redisTemplateRef = "batchRedisTemplate",
	keyValueTemplateRef = "batchRedisKeyValueTemplate"
)
public class BatchRedisConfig {
    @Bean
    public LettuceConnectionFactory batchRedisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6380));
    }

    @Bean(name = "batchRedisTemplate")
    public RedisTemplate<String, Object> batchRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(batchRedisConnectionFactory());
        return template;
    }

	@Bean(name = "batchRedisKeyValueTemplate")
	public KeyValueTemplate batchRedisKeyValueTemplate() {
		return new RedisKeyValueTemplate(
			new RedisKeyValueAdapter(batchRedisTemplate()),
			new RedisMappingContext()
		);
	}
}
