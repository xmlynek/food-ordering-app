package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.MenuItemUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import java.io.IOException;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantMenuItemService {

  Page<MenuItem> getWholeRestaurantMenu(UUID restaurantId, Pageable pageable);

  MenuItem getMenuItemById(UUID restaurantId, UUID menuItemId);

  MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem, MultipartFile image)
      throws IOException;

  MenuItem updateMenuItem(UUID restaurantId, UUID menuItemId, MenuItemUpdateRequest menuItemUpdateRequest);

  void deleteMenuItem(UUID restaurantId, UUID menuItemId);
}
