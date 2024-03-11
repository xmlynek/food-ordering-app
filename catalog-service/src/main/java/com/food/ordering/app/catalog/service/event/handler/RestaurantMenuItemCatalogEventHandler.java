package com.food.ordering.app.catalog.service.event.handler;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.mapper.MenuItemMapper;
import com.food.ordering.app.catalog.service.service.RestaurantMenuItemServiceImpl;
import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventHandlers;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventHandlersBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class RestaurantMenuItemCatalogEventHandler {

  private final MenuItemMapper menuItemMapper;
  private final RestaurantMenuItemServiceImpl menuItemService;


  public ReactiveDomainEventHandlers domainEventHandlers() {
    return ReactiveDomainEventHandlersBuilder.forAggregateType(
            "com.food.ordering.app.restaurant.service.entity.MenuItem")
        .onEvent(RestaurantMenuItemCreatedEvent.class, this::createMenuItem)
        .onEvent(RestaurantMenuItemRevisedEvent.class, this::reviseMenuItem)
        .onEvent(RestaurantMenuItemDeletedEvent.class, this::deleteMenuItem).build();
  }


  private Mono<Void> createMenuItem(DomainEventEnvelope<RestaurantMenuItemCreatedEvent> de) {
    return Mono.defer(() -> {
      String menuItemId = de.getAggregateId();
      RestaurantMenuItemCreatedEvent event = de.getEvent();
      String restaurantId = event.restaurantId();

      log.info(
          "Handling RestaurantMenuItemCreatedEvent for restaurant with ID {} and menu item with ID {}",
          restaurantId, menuItemId);

      MenuItem menuItem = menuItemMapper.restaurantMenuItemCreatedEvent(menuItemId, event);

      return menuItemService.addMenuItem(restaurantId, menuItem).doOnSuccess(
          r -> log.info("MenuItem with ID {} in restaurant with ID {} created", r.getId(),
              restaurantId)).then();
    }).onErrorResume(e -> {
      log.error("Error creating menu item with ID {} in restaurant with ID {}: {}",
          de.getAggregateId(), de.getEvent().restaurantId(), e.getMessage(), e);
      return Mono.error(e);
    });
  }

  public Mono<Void> reviseMenuItem(DomainEventEnvelope<RestaurantMenuItemRevisedEvent> de) {
    return Mono.defer(() -> {
      String menuItemId = de.getAggregateId();
      RestaurantMenuItemRevisedEvent event = de.getEvent();
      String restaurantId = event.restaurantId();
      log.info(
          "Handling RestaurantMenuItemRevisedEvent for restaurant with ID {} and menu item with ID {}",
          restaurantId, menuItemId);

      MenuItem menuItem = menuItemMapper.restaurantMenuItemRevisedEvent(menuItemId, event);

      return menuItemService.reviseMenuItem(restaurantId, menuItem).doOnSuccess(
          r -> log.info("MenuItem with ID {} in restaurant with ID {} revised", r.getId(),
              restaurantId)).then();
    }).onErrorResume(e -> {
      log.error("Error revising menu item with ID {} in restaurant with ID {}: {}",
          de.getAggregateId(), de.getEvent().restaurantId(), e.getMessage(), e);
      return Mono.error(e);
    });
  }

  private Mono<Void> deleteMenuItem(DomainEventEnvelope<RestaurantMenuItemDeletedEvent> de) {
    // TODO handle deletion of restaurant menus
    return Mono.defer(() -> {
      String menuItemId = de.getAggregateId();
      String restaurantId = de.getEvent().restaurantId();

      log.info(
          "Handling RestaurantMenuItemDeletedEvent for menu item with ID {} in restaurant with ID {}",
          menuItemId, restaurantId);

      return menuItemService.deleteMenuItem(restaurantId, menuItemId)
          .doOnSuccess(
              r -> log.info("MenuItem with ID {} in restaurant with ID {} was deleted", menuItemId,
                  restaurantId)).then();
    }).onErrorResume(e -> {
      log.error("Error deleting menu item with ID {} in restaurant with ID {}: {}",
          de.getAggregateId(), de.getEvent().restaurantId(), e.getMessage(), e);
      return Mono.error(e);
    });
  }
}
