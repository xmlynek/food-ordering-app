package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.dto.RestaurantResponse;
import com.food.ordering.app.restaurant.service.mapper.RestaurantMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<List<RestaurantResponse>> getPrincipalRestaurants() {
    List<RestaurantResponse> response = restaurantService.getAllRestaurants().stream()
        .map(restaurantMapper::restaurantEntityToRestaurantResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable UUID restaurantId) {
    RestaurantResponse response = restaurantMapper.restaurantEntityToRestaurantResponse(
        restaurantService.getRestaurantById(restaurantId));
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<RestaurantResponse> createRestaurant(
      @Valid @RequestBody RestaurantRequest restaurantRequest) {
    RestaurantResponse response = restaurantMapper.restaurantEntityToRestaurantResponse(
        restaurantService.createRestaurant(
            restaurantMapper.restaurantRequestToRestaurantEntity(restaurantRequest)));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable UUID restaurantId,
      @Valid @RequestBody RestaurantRequest restaurantRequest) {
    RestaurantResponse response = restaurantMapper.restaurantEntityToRestaurantResponse(
        restaurantService.updateRestaurant(restaurantId, restaurantRequest));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
    restaurantService.deleteRestaurant(restaurantId);
    return ResponseEntity.noContent().build();
  }
}
