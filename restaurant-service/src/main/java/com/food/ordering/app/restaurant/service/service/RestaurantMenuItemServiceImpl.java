package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.dto.MenuItemRequest;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import com.food.ordering.app.restaurant.service.exception.MenuItemNotFoundException;
import com.food.ordering.app.restaurant.service.exception.RestaurantNotFoundException;
import com.food.ordering.app.restaurant.service.repository.MenuItemRepository;
import com.food.ordering.app.restaurant.service.repository.RestaurantRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemServiceImpl implements
    RestaurantMenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  public List<MenuItem> getWholeRestaurantMenu(UUID restaurantId) {
    return menuItemRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public MenuItem getMenuItemById(UUID restaurantId, UUID menuItemId) {
    return menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
  }

  @Transactional
  @Override
  public MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    menuItem.setIsDeleted(false);
    menuItem.setIsAvailable(true);
    menuItem.setRestaurant(restaurant);

    return menuItemRepository.save(menuItem);
  }

  @Override
  @Transactional
  public MenuItem updateMenuItem(UUID restaurantId, UUID menuItemId, MenuItemRequest menuItemRequest) {
    MenuItem menuItem = menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));

    menuItem.setName(menuItemRequest.name());
    menuItem.setDescription(menuItemRequest.description());
    menuItem.setPrice(menuItemRequest.price());

    return menuItemRepository.save(menuItem);
  }

  @Override
  @Transactional
  public void deleteMenuItem(UUID restaurantId, UUID menuItemId) {
    MenuItem menuItem = menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
    menuItem.setIsDeleted(true);
//    menuItemRepository.save(menuItem);
  }
}
