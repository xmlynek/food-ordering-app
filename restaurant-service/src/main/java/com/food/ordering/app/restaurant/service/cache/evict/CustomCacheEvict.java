package com.food.ordering.app.restaurant.service.cache.evict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCacheEvict {

  String cacheName();

  String keyPattern() default "";
}
