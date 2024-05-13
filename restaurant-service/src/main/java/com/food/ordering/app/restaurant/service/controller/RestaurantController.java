package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.cache.evict.CustomCacheEvict;
import com.food.ordering.app.restaurant.service.config.RedisConfig;
import com.food.ordering.app.restaurant.service.dto.BasicRestaurantResponse;
import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.dto.RestaurantResponse;
import com.food.ordering.app.restaurant.service.dto.RestaurantUpdateRequest;
import com.food.ordering.app.restaurant.service.mapper.RestaurantMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@CacheConfig(keyGenerator = "restaurantCacheKeyGenerator", cacheNames = {
    RedisConfig.RESTAURANT_CACHE_NAME, RedisConfig.RESTAURANTS_CACHE_NAME})
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final RestaurantMapper restaurantMapper;


  @GetMapping
  @Cacheable(value = RedisConfig.RESTAURANTS_CACHE_NAME, key = "{@principalProviderImpl.name, #pageable.pageNumber, #pageable.pageSize}")
  public Page<BasicRestaurantResponse> getPrincipalRestaurants(
      @SortDefault(value = "name", caseSensitive = false) @PageableDefault Pageable pageable) {
    return restaurantService.getAllRestaurants(pageable)
        .map(restaurantMapper::restaurantEntityToBasicRestaurantResponse);
  }

  @GetMapping("/{restaurantId}")
  @Cacheable(value = RedisConfig.RESTAURANT_CACHE_NAME)
  public RestaurantResponse getRestaurantById(@PathVariable UUID restaurantId) {
    return restaurantMapper.restaurantEntityToRestaurantResponse(
        restaurantService.getRestaurantById(restaurantId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CustomCacheEvict(cacheName = RedisConfig.RESTAURANTS_CACHE_NAME)
  public BasicRestaurantResponse createRestaurant(
      @Valid @RequestBody RestaurantRequest restaurantRequest) {
    return restaurantMapper.restaurantEntityToBasicRestaurantResponse(
        restaurantService.createRestaurant(
            restaurantMapper.restaurantRequestToRestaurantEntity(restaurantRequest)));
  }

  @PutMapping("/{restaurantId}")
  @CacheEvict(value = RedisConfig.RESTAURANT_CACHE_NAME)
  @CustomCacheEvict(cacheName = RedisConfig.RESTAURANTS_CACHE_NAME)
  public BasicRestaurantResponse updateRestaurant(@PathVariable UUID restaurantId,
      @Valid @RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
    return restaurantMapper.restaurantEntityToBasicRestaurantResponse(
        restaurantService.updateRestaurant(restaurantId, restaurantUpdateRequest));
  }

  @DeleteMapping("/{restaurantId}")
  @Caching(evict = {@CacheEvict(value = RedisConfig.RESTAURANT_CACHE_NAME)})
  @CustomCacheEvict(cacheName = RedisConfig.RESTAURANTS_CACHE_NAME)
  public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
    restaurantService.deleteRestaurant(restaurantId);
    return ResponseEntity.noContent().build();
  }
}
