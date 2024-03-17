package com.food.ordering.app.catalog.service.controller;

import com.food.ordering.app.catalog.service.dto.MenuItemDto;
import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.service.RestaurantMenuItemQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/catalog/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantMenuItemQueryController {

  private final RestaurantMenuItemQueryService menuItemQueryService;

  @GetMapping
  public Mono<Page<MenuItem>> getRestaurantMenuItems(@PathVariable String restaurantId,
      @PageableDefault final Pageable pageable) {
    return menuItemQueryService.findAllMenuItems(restaurantId, pageable);
  }

  @GetMapping("/{menuItemId}")
  public Mono<MenuItemDto> getRestaurantMenuItemById(@PathVariable String restaurantId,
      @PathVariable String menuItemId) {
    return menuItemQueryService.findMenuItemById(restaurantId, menuItemId);
  }
}
