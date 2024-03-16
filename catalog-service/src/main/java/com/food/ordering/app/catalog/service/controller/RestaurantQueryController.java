package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import com.food.ordering.app.catalog.service.service.RestaurantQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/catalog/restaurants")
public class RestaurantQueryController {

  private final RestaurantQueryService restaurantQueryService;

  @Autowired
  public RestaurantQueryController(RestaurantQueryService restaurantQueryService) {
    this.restaurantQueryService = restaurantQueryService;
  }

  @GetMapping
  public Mono<Page<BasicRestaurantDto>> getAllRestaurants(
      @RequestParam(name = "searchValue", required = false, defaultValue = "") String searchValue,
      @PageableDefault final Pageable pageable) {
    return restaurantQueryService.findAllRestaurants(pageable, searchValue);
  }

  @GetMapping("/{id}")
  public Mono<FullRestaurantDto> getRestaurantById(@PathVariable String id) {
    return restaurantQueryService.findRestaurantById(id);
  }
}
