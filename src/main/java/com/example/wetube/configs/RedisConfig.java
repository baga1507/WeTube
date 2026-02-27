package com.example.wetube.configs;

import com.example.wetube.dto.PaginatedRecommendationsDto;
import com.example.wetube.dto.VideoDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Jackson2JsonRedisSerializer<PaginatedRecommendationsDto> paginatedSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, PaginatedRecommendationsDto.class);

        Jackson2JsonRedisSerializer<VideoDto> videoSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, VideoDto.class);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("RECOMMENDATION_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(paginatedSerializer)))
                .withCacheConfiguration("VIDEO_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(videoSerializer)));

        return builder.build();
    }
}
