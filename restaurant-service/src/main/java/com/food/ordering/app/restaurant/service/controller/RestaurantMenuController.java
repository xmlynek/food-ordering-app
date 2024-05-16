package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.config.RedisConfig;
import com.food.ordering.app.restaurant.service.dto.MenuItemRequest;
import com.food.ordering.app.restaurant.service.dto.MenuItemResponse;
import com.food.ordering.app.restaurant.service.dto.MenuItemUpdateRequest;
import com.food.ordering.app.restaurant.service.mapper.MenuItemMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantMenuItemService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@CacheConfig(cacheNames = {RedisConfig.MENU_ITEM_CACHE_NAME,
    RedisConfig.MENU_ITEMS_CACHE_NAME}, keyGenerator = "menuItemCacheKeyGenerator")
public class RestaurantMenuController {

  private final RestaurantMenuItemService menuItemService;
  private final MenuItemMapper menuItemMapper;

  @GetMapping
//  @Cacheable(value = RedisConfig.MENU_ITEMS_CACHE_NAME, key = "{@principalProviderImpl.name, #restaurantId, #pageable.pageNumber, #pageable.pageSize}")
  public Page<MenuItemResponse> getRestaurantMenu(@PathVariable UUID restaurantId,
      @SortDefault(value = "name", caseSensitive = false) @PageableDefault Pageable pageable) {
    return menuItemService.getWholeRestaurantMenu(restaurantId,
        pageable).map(menuItemMapper::menuItemToMenuItemResponse);
  }

  @GetMapping("/{menuId}")
  @Cacheable(value = RedisConfig.MENU_ITEM_CACHE_NAME)
  public MenuItemResponse getRestaurantMenuById(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId) {
    return menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.getMenuItemById(restaurantId, menuId));
  }

  @PostMapping
  @CacheEvict(value = RedisConfig.RESTAURANT_CACHE_NAME, keyGenerator = "restaurantCacheKeyGenerator")
//  @CustomCacheEvict(cacheName = RedisConfig.MENU_ITEMS_CACHE_NAME)
  @ResponseStatus(HttpStatus.CREATED)
  public MenuItemResponse createRestaurantMenu(@PathVariable UUID restaurantId,
      @Valid @RequestPart("menuItemRequest") MenuItemRequest menuItemRequest,
      @RequestPart("image") MultipartFile image) throws IOException {

    return menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.createMenuItem(restaurantId,
            menuItemMapper.menuItemRequestToMenuItem(menuItemRequest), image));
  }

  @PutMapping("/{menuId}")
  @Caching(evict = {
      @CacheEvict(RedisConfig.MENU_ITEM_CACHE_NAME),
      @CacheEvict(value = RedisConfig.RESTAURANT_CACHE_NAME, keyGenerator = "restaurantCacheKeyGenerator")
  })
//  @CustomCacheEvict(cacheName = RedisConfig.MENU_ITEMS_CACHE_NAME)
  public MenuItemResponse updateRestaurantMenu(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId,
      @RequestPart(value = "image", required = false) MultipartFile image,
      @Valid @RequestPart("menuItemUpdateRequest") MenuItemUpdateRequest menuItemUpdateRequest)
      throws IOException {
    log.info("Updating menu item with id: {} within restaurant id: {}.", menuId, restaurantId);

    MenuItemResponse updatedMenuItem = menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.updateMenuItem(restaurantId, menuId, menuItemUpdateRequest, image));

    log.info("Menu item with id: {} within restaurant id: {} was updated", menuId, restaurantId);
    return updatedMenuItem;
  }

  @DeleteMapping("/{menuId}")
  @Caching(evict = {
      @CacheEvict(RedisConfig.MENU_ITEM_CACHE_NAME),
      @CacheEvict(value = RedisConfig.RESTAURANT_CACHE_NAME, keyGenerator = "restaurantCacheKeyGenerator")
  })
//  @CustomCacheEvict(cacheName = RedisConfig.MENU_ITEMS_CACHE_NAME)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteRestaurantMenu(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId) {
    menuItemService.deleteMenuItem(restaurantId, menuId);
    return ResponseEntity.noContent().build();
  }
}
