package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.dto.MenuItemRequest;
import com.food.ordering.app.restaurant.service.dto.MenuItemResponse;
import com.food.ordering.app.restaurant.service.mapper.MenuItemMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantMenuItemService;
import jakarta.validation.Valid;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantMenuController {

  private final RestaurantMenuItemService menuItemService;
  private final MenuItemMapper menuItemMapper;

  @GetMapping
  public ResponseEntity<List<MenuItemResponse>> getWholeRestaurantMenu(
      @PathVariable UUID restaurantId) {
    List<MenuItemResponse> menuItems = menuItemService.getWholeRestaurantMenu(restaurantId).stream()
        .map(menuItemMapper::menuItemToMenuItemResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(menuItems);
  }

  @GetMapping("/{menuId}")
  public ResponseEntity<MenuItemResponse> getRestaurantMenuById(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId) {
    MenuItemResponse menuItem = menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.getMenuItemById(restaurantId, menuId));
    return ResponseEntity.ok(menuItem);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<MenuItemResponse> createRestaurantMenu(@PathVariable UUID restaurantId,
      @Valid @RequestPart("menuItemRequest") MenuItemRequest menuItemRequest,
      @RequestPart("image") MultipartFile image) throws IOException {

    MenuItemResponse newMenuItem = menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.createMenuItem(restaurantId,
            menuItemMapper.menuItemRequestToMenuItem(menuItemRequest), image));

    return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
  }

  @PutMapping("/{menuId}")
  public ResponseEntity<MenuItemResponse> updateRestaurantMenu(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId,
      @Valid @RequestBody MenuItemRequest menuItemRequest) {
    MenuItemResponse updatedMenuItem = menuItemMapper.menuItemToMenuItemResponse(
        menuItemService.updateMenuItem(restaurantId, menuId, menuItemRequest));
    return ResponseEntity.ok(updatedMenuItem);
  }

  @DeleteMapping("/{menuId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteRestaurantMenu(@PathVariable UUID restaurantId,
      @PathVariable UUID menuId) {
    menuItemService.deleteMenuItem(restaurantId, menuId);
    return ResponseEntity.noContent().build();
  }
}