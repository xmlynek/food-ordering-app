package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.MenuItemUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantMenuItemService {

  List<MenuItem> getWholeRestaurantMenu(UUID restaurantId);

  MenuItem getMenuItemById(UUID restaurantId, UUID menuItemId);

  MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem, MultipartFile image)
      throws IOException;

  MenuItem updateMenuItem(UUID restaurantId, UUID menuItemId, MenuItemUpdateRequest menuItemUpdateRequest);

  void deleteMenuItem(UUID restaurantId, UUID menuItemId);
}
