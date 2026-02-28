package com.example.wetube.configs;

import com.example.wetube.dto.CommentSliceDto;
import com.example.wetube.dto.RecommendationPageDto;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Jackson2JsonRedisSerializer<RecommendationPageDto> paginatedSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, RecommendationPageDto.class);

        Jackson2JsonRedisSerializer<VideoDto> videoSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, VideoDto.class);

        Jackson2JsonRedisSerializer<CommentSliceDto> commentSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, CommentSliceDto.class);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration("RECOMMENDATION_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(paginatedSerializer)))
                .withCacheConfiguration("VIDEO_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(videoSerializer)))
                .withCacheConfiguration("VIDEO_COMMENT_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(commentSerializer)))
                .withCacheConfiguration("USER_COMMENT_CACHE",
                        defaultConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(commentSerializer)));

        return builder.build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
