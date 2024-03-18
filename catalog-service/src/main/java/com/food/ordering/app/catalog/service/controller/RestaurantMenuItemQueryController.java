package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.dto.MenuItemDto;
import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.service.RestaurantMenuItemQueryService;
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
@RequestMapping("/api/catalog/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class RestaurantMenuItemQueryController {

  private final RestaurantMenuItemQueryService menuItemQueryService;

  @GetMapping
  public Mono<Page<MenuItem>> getRestaurantMenuItems(@PathVariable String restaurantId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    log.info("Fetching menu items for restaurantId: {}", restaurantId);
    return menuItemQueryService.findAllMenuItems(restaurantId,
        Pageable.ofSize(size).withPage(page));
  }

  @GetMapping("/{menuItemId}")
  public Mono<MenuItemDto> getRestaurantMenuItemById(@PathVariable String restaurantId,
      @PathVariable String menuItemId) {
    log.info("Fetching menu item for restaurantId: {}, menuItemId: {}", restaurantId, menuItemId);
    return menuItemQueryService.findMenuItemById(restaurantId, menuItemId);
  }
}
