package com.food.ordering.app.catalog.service.service;

import com.food.ordering.app.catalog.service.dto.MenuItemDto;
import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.mapper.MenuItemMapper;
import com.food.ordering.app.catalog.service.repository.RestaurantRepository;
import com.food.ordering.app.common.exception.MenuItemNotFoundException;
import com.food.ordering.app.common.exception.RestaurantNotFoundException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantMenuItemQueryServiceImpl implements RestaurantMenuItemQueryService {

  private final RestaurantRepository restaurantRepository;
  private final MenuItemMapper menuItemMapper;

  @Override
  public Mono<Page<MenuItem>> findAllMenuItems(String restaurantId, Pageable pageable) {
    return restaurantRepository.findByIdAndIsAvailableTrue(restaurantId)
        .flatMap(restaurant -> {
          List<MenuItem> menuItems = restaurant.getMenuItems();
          int totalItems = menuItems.size();

          int start = (int) pageable.getOffset();
          int end = Math.min((start + pageable.getPageSize()), totalItems);
          List<MenuItem> paginatedItems = start <= end ? menuItems.subList(start, end) : Collections.emptyList();

          Page<MenuItem> page = new PageImpl<>(paginatedItems, pageable, totalItems);
          return Mono.just(page);
        })
        .switchIfEmpty(Mono.error(new RestaurantNotFoundException(restaurantId)));
  }

  @Override
  public Mono<MenuItemDto> findMenuItemById(String restaurantId, String menuItemId) {
    return restaurantRepository.findByIdAndIsAvailableTrue(restaurantId)
        .flatMap(restaurant -> Flux.fromIterable(restaurant.getMenuItems())
            .filter(menuItem -> menuItem.getId().equals(menuItemId))
            .singleOrEmpty()
            .map(menuItemMapper::menuItemToMenuItemDto)
            .switchIfEmpty(Mono.error(new MenuItemNotFoundException(menuItemId))))
        .switchIfEmpty(Mono.error(new RestaurantNotFoundException(restaurantId)));
  }
}
