package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.MenuItemRequest;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import java.util.List;
import java.util.UUID;

public interface RestaurantMenuItemService {

  List<MenuItem> getWholeRestaurantMenu(UUID restaurantId);

  MenuItem getMenuItemById(UUID restaurantId, UUID menuItemId);

  MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem);

  MenuItem updateMenuItem(UUID restaurantId, UUID menuItemId, MenuItemRequest menuItemRequest);

  void deleteMenuItem(UUID restaurantId, UUID menuItemId);
}
