package com.food.ordering.app.restaurant.service.cache.keygenerator;

import com.food.ordering.app.restaurant.service.principal.PrincipalProvider;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantCacheKeyGenerator implements KeyGenerator {

  private final PrincipalProvider principalProvider;

  @Override
  @NonNull
  public Object generate(@NonNull Object target, @NonNull Method method,
      @NonNull Object... params) {
    return principalProvider.getName() + ":" + params[0].toString();
  }
}
