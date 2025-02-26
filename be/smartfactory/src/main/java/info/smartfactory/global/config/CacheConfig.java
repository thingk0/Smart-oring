package info.smartfactory.global.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                                                            .entryTtl(Duration.ofHours(1))
                                                                            .serializeKeysWith(
                                                                                RedisSerializationContext.SerializationPair.fromSerializer(
                                                                                    new StringRedisSerializer()))
                                                                            .serializeValuesWith(
                                                                                RedisSerializationContext.SerializationPair.fromSerializer(
                                                                                    new GenericJackson2JsonRedisSerializer()))
                                                                            .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(cacheConfiguration)
                                .transactionAware()
                                .build();
    }
}

