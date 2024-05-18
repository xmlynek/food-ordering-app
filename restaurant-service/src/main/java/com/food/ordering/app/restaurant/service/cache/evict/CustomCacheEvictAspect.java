package com.food.ordering.app.restaurant.service.cache.evict;

import com.food.ordering.app.restaurant.service.principal.PrincipalProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomCacheEvictAspect {

  private final RedisTemplate<String, Object> redisTemplate;
  private final CacheProperties cacheProperties;
  private final PrincipalProvider principalProvider;

  @Around("@annotation(customCacheEvict)")
  public Object evictCache(ProceedingJoinPoint joinPoint, CustomCacheEvict customCacheEvict)
      throws Throwable {
    Object result = joinPoint.proceed();

    String keyPattern = customCacheEvict.keyPattern().isEmpty() ? (principalProvider.getName() + "*")
        : customCacheEvict.keyPattern();

    String pattern = cacheProperties.getRedis().getKeyPrefix() + customCacheEvict.cacheName() + "::"
        + keyPattern;

    Optional.ofNullable(redisTemplate.keys(pattern)).ifPresent(keys -> {
      log.info("Cache Evicting keys {}", keys);
      redisTemplate.delete(keys);
    });
    return result;
  }
}
