package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.order.service.entity.MenuItem;
import java.util.UUID;

public interface RestaurantMenuItemService {

  MenuItem addMenuItem(UUID restaurantId, MenuItem menuItem);

  MenuItem reviseMenuItem(UUID menuItemId, RestaurantMenuItemRevisedEvent event);

  void deleteMenuItem(UUID menuItemId);
}
