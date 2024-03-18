package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import com.food.ordering.app.catalog.service.service.RestaurantQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/catalog/restaurants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class RestaurantQueryController {

  private final RestaurantQueryService restaurantQueryService;


  @GetMapping
  public Mono<Page<BasicRestaurantDto>> getAllRestaurants(
      @RequestParam(name = "searchValue", required = false, defaultValue = "") String searchValue,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    log.info("Fetching all restaurants");
    return restaurantQueryService.findAllRestaurants(Pageable.ofSize(size).withPage(page),
        searchValue);
  }

  @GetMapping("/{id}")
  public Mono<FullRestaurantDto> getRestaurantById(@PathVariable String id) {
    log.info("Fetching restaurant by id: {}", id);
    return restaurantQueryService.findRestaurantById(id);
  }
}
