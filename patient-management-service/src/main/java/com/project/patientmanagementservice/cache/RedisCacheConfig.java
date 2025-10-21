package com.project.patientmanagementservice.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

  // this will be used to define how we will connect to the Redis and basic configuration
  @Bean
  public RedisCacheManager cacheManager(
      RedisConnectionFactory connectionFactory) {
    ObjectMapper mapperForSerialization = new ObjectMapper();
    mapperForSerialization.registerModule(new JavaTimeModule());
    // disabling writing date as timestamp, date will be written as ISO date time format.
    mapperForSerialization.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    // when serializing an object into redis, we want to store the type of it so that it will retain the information
    // during deserialization.
    mapperForSerialization.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
            DefaultTyping.EVERYTHING, As.PROPERTY);

    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(
            mapperForSerialization);

    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(5)).disableCachingNullValues()
         // serialize key as string
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new StringRedisSerializer()))
        // serializer value as serializer created above
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                serializer));

    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(config)
        .build();
  }
}
