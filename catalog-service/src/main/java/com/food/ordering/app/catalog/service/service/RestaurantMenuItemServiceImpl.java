package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.repository.MenuItemRepository;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {

  public static final String MENU_ITEM_RELATION_NAME = "menu_item";

  private final RestaurantRepository restaurantRepository;
  private final MenuItemRepository menuItemRepository;


  @Override
  public Mono<MenuItem> addMenuItem(String restaurantId, MenuItem menuItem) {
    return restaurantRepository.existsById(restaurantId)
        .flatMap(restaurant -> {
          menuItem.setJoinField(new JoinField<>(MENU_ITEM_RELATION_NAME, restaurantId));
          return menuItemRepository.save(menuItem);
        })
        .switchIfEmpty(Mono.error(new RestaurantNotFoundException(restaurantId)));
  }

  @Override
  public Mono<MenuItem> reviseMenuItem(MenuItem updatedMenuItem) {
    String menuItemId = updatedMenuItem.getId();
    return menuItemRepository.findById(menuItemId)
        .switchIfEmpty(Mono.error(new MenuItemNotFoundException(menuItemId)))
        .flatMap(existingItem -> {
          existingItem.setName(updatedMenuItem.getName() != null ? updatedMenuItem.getName()
              : existingItem.getName());
          existingItem.setDescription(
              updatedMenuItem.getDescription() != null ? updatedMenuItem.getDescription()
                  : existingItem.getDescription());
          existingItem.setPrice(updatedMenuItem.getPrice() != null ? updatedMenuItem.getPrice()
              : existingItem.getPrice());
          existingItem.setIsAvailable(
              updatedMenuItem.getIsAvailable() != null ? updatedMenuItem.getIsAvailable()
                  : existingItem.getIsAvailable());
          existingItem.setImageUrl(
              updatedMenuItem.getImageUrl() != null ? updatedMenuItem.getImageUrl()
                  : existingItem.getImageUrl());
          existingItem.setLastModifiedAt(LocalDateTime.now());

          return menuItemRepository.save(existingItem);
        });
  }

  @Override
  public Mono<Void> deleteMenuItem(String menuItemId) {
    return menuItemRepository.findById(menuItemId)
        .switchIfEmpty(Mono.error(new MenuItemNotFoundException(menuItemId)))
        .flatMap(menuItemRepository::delete);
  }
}
