package com.food.ordering.app.order.service.service;

import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.order.service.entity.MenuItem;
import com.food.ordering.app.order.service.entity.Restaurant;
import com.food.ordering.app.order.service.repository.RestaurantMenuItemRepository;
import com.food.ordering.app.order.service.repository.RestaurantRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {

  private final RestaurantRepository restaurantRepository;
  private final RestaurantMenuItemRepository menuItemRepository;


  @Override
  public MenuItem addMenuItem(UUID restaurantId, MenuItem menuItem) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    menuItem.setRestaurant(restaurant);
    restaurant.getMenuItems().add(menuItem);
    return menuItemRepository.save(menuItem);
  }

  @Override
  public MenuItem reviseMenuItem(UUID menuItemId, RestaurantMenuItemRevisedEvent event) {

    MenuItem menuItem = menuItemRepository.findById(menuItemId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));

    menuItem.setDescription(event.description());
    menuItem.setImageUrl(event.imageUrl());
    menuItem.setIsAvailable(event.isAvailable());
    menuItem.setName(event.name());
    menuItem.setPrice(event.price());
    menuItem.setLastModifiedAt(event.lastModifiedAt());

    return menuItemRepository.save(menuItem);
  }

  @Override
  public void deleteMenuItem(UUID menuItemId) {
    MenuItem menuItem = menuItemRepository.findById(menuItemId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));

    menuItem.setIsAvailable(false);
    menuItem.setIsDeleted(true);
    menuItemRepository.save(menuItem);
  }
}
