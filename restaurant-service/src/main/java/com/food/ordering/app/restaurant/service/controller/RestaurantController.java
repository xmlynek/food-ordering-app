package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.dto.BasicRestaurantResponse;
import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.dto.RestaurantResponse;
import com.food.ordering.app.restaurant.service.dto.RestaurantUpdateRequest;
import com.food.ordering.app.restaurant.service.mapper.RestaurantMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final RestaurantMapper restaurantMapper;


  @GetMapping
//  @Cacheable(value = "restaurants", key = "{#root.methodName, #pageable.pageNumber, #pageable.pageSize}")
  public Page<BasicRestaurantResponse> getPrincipalRestaurants(
      @SortDefault(value = "name", caseSensitive = false) @PageableDefault Pageable pageable) {
    Page<BasicRestaurantResponse> response = restaurantService.getAllRestaurants(pageable)
        .map(restaurantMapper::restaurantEntityToBasicRestaurantResponse);
//    return ResponseEntity.ok(response);
    return response;
//    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{restaurantId}")
  @Cacheable(value = "restaurant", key = "{#restaurantId, @redisConfig.usernameKey}")
  public RestaurantResponse getRestaurantById(@PathVariable UUID restaurantId) {
    RestaurantResponse response = restaurantMapper.restaurantEntityToRestaurantResponse(
        restaurantService.getRestaurantById(restaurantId));
    return response;
//    return ResponseEntity.ok(response);
  }

  @PostMapping
  @CachePut(value = "restaurant", key = "#result.body.id()") // Cache the newly created restaurant
  @CacheEvict(value = "restaurants", allEntries = true) // Invalidate all entries from the restaurants cache
  public ResponseEntity<BasicRestaurantResponse> createRestaurant(
      @Valid @RequestBody RestaurantRequest restaurantRequest) {
    BasicRestaurantResponse response = restaurantMapper.restaurantEntityToBasicRestaurantResponse(
        restaurantService.createRestaurant(
            restaurantMapper.restaurantRequestToRestaurantEntity(restaurantRequest)));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{restaurantId}")
  @CacheEvict(value = "restaurants", allEntries = true) // Invalidate all entries from the restaurants cache
  @CachePut(value = "restaurant", key = "#restaurantId") // Update the cache for this specific restaurant
  public ResponseEntity<BasicRestaurantResponse> updateRestaurant(@PathVariable UUID restaurantId,
      @Valid @RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
    BasicRestaurantResponse response = restaurantMapper.restaurantEntityToBasicRestaurantResponse(
        restaurantService.updateRestaurant(restaurantId, restaurantUpdateRequest));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{restaurantId}")
  @CacheEvict(value = "restaurants", allEntries = true) // Invalidate all entries from the restaurants cache
//  @CacheEvict(value = "restaurant", key = "#restaurantId") // Evict the specific restaurant from the cache
  public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
    restaurantService.deleteRestaurant(restaurantId);
    return ResponseEntity.noContent().build();
  }
}
