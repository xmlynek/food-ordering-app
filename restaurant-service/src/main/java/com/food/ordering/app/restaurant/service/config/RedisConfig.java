package com.food.ordering.app.restaurant.service.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final JsonMapper jsonMapper;


  public String getUsernameKey() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

//  @Bean
//  public RedisCacheConfiguration cacheConfiguration() {
//    return RedisCacheConfiguration.defaultCacheConfig()
//        .entryTtl(Duration.ofMinutes(10))
//        .disableCachingNullValues()
//        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(jsonMapper)));
//  }

//  @Bean
//  public RedisCacheConfiguration cacheConfiguration(ObjectMapper mapper) {
//    var myMapper = mapper.copy().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        .activateDefaultTyping(jsonMapper.getPolymorphicTypeValidator(),
//            ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
//
//    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))
//        .disableCachingNullValues().serializeValuesWith(
//            SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(myMapper)));
//  }

//  @Bean
//  public RedisCacheConfiguration cacheConfiguration() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JavaTimeModule());  // Support for Java 8 Time types
//    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//    objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
//    return RedisCacheConfiguration.defaultCacheConfig()
//        .entryTtl(Duration.ofMinutes(10))
//        .disableCachingNullValues()
//        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
//  }
}
