package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {

  private final RestaurantRepository restaurantRepository;


  @Override
  public Mono<Restaurant> addMenuItem(String restaurantId, MenuItem menuItem) {
    return restaurantRepository.findById(restaurantId)
        .flatMap(restaurant -> {
          List<MenuItem> menuItems = restaurant.getMenuItems();
          menuItems.add(menuItem);
          return restaurantRepository.save(restaurant);
        }).switchIfEmpty(Mono.error(new RestaurantNotFoundException(restaurantId)));
  }

  @Override
  public Mono<Restaurant> reviseMenuItem(String restaurantId, MenuItem updatedMenuItem) {
    return restaurantRepository.findById(restaurantId)
        .flatMap(restaurant -> {

          boolean itemExists = restaurant.getMenuItems().stream()
              .anyMatch(item -> item.getId().equals(updatedMenuItem.getId()));

          if (!itemExists) {
            return Mono.error(new MenuItemNotFoundException(updatedMenuItem.getId()));
          }

          List<MenuItem> updatedMenuItems = restaurant.getMenuItems()
              .stream()
              .map(menuItem -> {
                if (menuItem.getId().equals(updatedMenuItem.getId())) {
                  updatedMenuItem.setCreatedAt(menuItem.getCreatedAt());
                  return updatedMenuItem;
                } else {
                  return menuItem;
                }
              })
              .collect(Collectors.toList());

          restaurant.setMenuItems(updatedMenuItems);
          return restaurantRepository.save(restaurant);
        })
        .switchIfEmpty(Mono.error(
            new RestaurantNotFoundException(restaurantId)));
  }

  @Override
  public Mono<Restaurant> deleteMenuItem(String restaurantId, String menuItemId) {
    return restaurantRepository.findById(restaurantId)
        .flatMap(restaurant -> {
          List<MenuItem> updatedMenuItems = restaurant.getMenuItems().stream()
              .filter(menuItem -> !menuItemId.equals(menuItem.getId()))
              .collect(Collectors.toList());

          if (restaurant.getMenuItems().size() == updatedMenuItems.size()) {
            return Mono.error(new MenuItemNotFoundException(menuItemId));
          }

          restaurant.setMenuItems(updatedMenuItems);
          return restaurantRepository.save(restaurant);
        }).switchIfEmpty(Mono.error(new RestaurantNotFoundException(restaurantId)));
  }
}
