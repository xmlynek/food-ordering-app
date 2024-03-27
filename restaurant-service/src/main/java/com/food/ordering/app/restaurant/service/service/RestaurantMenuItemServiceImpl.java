package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import com.food.ordering.app.restaurant.service.dto.MenuItemUpdateRequest;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import com.food.ordering.app.restaurant.service.event.publisher.RestaurantMenuItemDomainEventPublisher;
import com.food.ordering.app.restaurant.service.mapper.MenuItemMapper;
import com.food.ordering.app.restaurant.service.repository.MenuItemRepository;
import com.food.ordering.app.restaurant.service.repository.RestaurantRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemServiceImpl implements
    RestaurantMenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final StorageService imageStorageService;
  private final RestaurantMenuItemDomainEventPublisher domainEventPublisher;
  private final MenuItemMapper menuItemMapper;

  @Override
  public Page<MenuItem> getWholeRestaurantMenu(UUID restaurantId, Pageable pageable) {
    return menuItemRepository.findAllByRestaurantIdAndRestaurantOwnerIdAndIsDeletedFalse(
        restaurantId, SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
  }

  @Override
  public MenuItem getMenuItemById(UUID restaurantId, UUID menuItemId) {
    return menuItemRepository.findByIdAndRestaurantIdAndIsDeletedFalse(menuItemId, restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
  }

  @Transactional
  @Override
  public MenuItem createMenuItem(UUID restaurantId, MenuItem menuItem, MultipartFile image)
      throws IOException {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    menuItem.setIsDeleted(false);
    menuItem.setIsAvailable(true);
    menuItem.setRestaurant(restaurant);
    menuItem.setCreatedAt(LocalDateTime.now());
    menuItem.setLastModifiedAt(LocalDateTime.now());

    MenuItem savedMenuItem = menuItemRepository.save(menuItem);

    if (image != null) {
      String imageUrl = imageStorageService.uploadFile(image, savedMenuItem.getId());
      savedMenuItem.setImageUrl(imageUrl);
    }

    RestaurantMenuItemCreatedEvent restaurantMenuItemCreatedEvent = menuItemMapper.menuItemToRestaurantMenuItemCreatedEvent(
        savedMenuItem);

    domainEventPublisher.publish(savedMenuItem,
        Collections.singletonList(restaurantMenuItemCreatedEvent));

    return savedMenuItem;
  }

  @Override
  @Transactional
  public MenuItem updateMenuItem(UUID restaurantId, UUID menuItemId,
      MenuItemUpdateRequest menuItemRequest) {
    MenuItem menuItem = menuItemRepository.findByIdAndRestaurantIdAndIsDeletedFalse(menuItemId,
            restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));

    menuItem.setName(menuItemRequest.name());
    menuItem.setDescription(menuItemRequest.description());
    menuItem.setPrice(menuItemRequest.price());
    menuItem.setIsAvailable(menuItemRequest.isAvailable());
    menuItem.setLastModifiedAt(LocalDateTime.now());

    MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

    RestaurantMenuItemRevisedEvent restaurantMenuItemRevisedEvent = menuItemMapper.menuItemToRestaurantMenuItemRevisedEvent(
        updatedMenuItem);

    domainEventPublisher.publish(updatedMenuItem,
        Collections.singletonList(restaurantMenuItemRevisedEvent));

    return updatedMenuItem;
  }

  @Override
  @Transactional
  public void deleteMenuItem(UUID restaurantId, UUID menuItemId) {
    MenuItem menuItem = menuItemRepository.findByIdAndRestaurantIdAndIsDeletedFalse(menuItemId,
            restaurantId)
        .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
    menuItem.setIsDeleted(true);
    menuItem.setIsAvailable(false);
    menuItem.setLastModifiedAt(LocalDateTime.now());

    RestaurantMenuItemDeletedEvent restaurantMenuItemDeletedEvent = menuItemMapper.menuItemToRestaurantMenuItemDeletedEvent(
        menuItem);

    domainEventPublisher.publish(menuItem,
        Collections.singletonList(restaurantMenuItemDeletedEvent));
  }
}
