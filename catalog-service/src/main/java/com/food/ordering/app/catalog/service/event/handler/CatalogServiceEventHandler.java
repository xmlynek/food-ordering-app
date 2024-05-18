package com.food.ordering.app.catalog.service.event.handler;

import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.mapper.MenuItemMapper;
import com.food.ordering.app.catalog.service.mapper.RestaurantMapper;
import com.food.ordering.app.catalog.service.service.RestaurantMenuItemService;
import com.food.ordering.app.catalog.service.service.RestaurantService;
import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
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
public class CatalogServiceEventHandler {

  private final RestaurantMapper restaurantMapper;
  private final MenuItemMapper menuItemMapper;
  private final RestaurantService restaurantService;
  private final RestaurantMenuItemService menuItemService;


  public ReactiveDomainEventHandlers domainEventHandlers() {
    return ReactiveDomainEventHandlersBuilder
        .forAggregateType("com.food.ordering.app.restaurant.service.entity.Restaurant")
        .onEvent(RestaurantCreatedEvent.class, this::createRestaurant)
        .onEvent(RestaurantRevisedEvent.class, this::reviseRestaurant)
        .onEvent(RestaurantDeletedEvent.class, this::deleteRestaurant)
        .andForAggregateType("com.food.ordering.app.restaurant.service.entity.MenuItem")
        .onEvent(RestaurantMenuItemCreatedEvent.class, this::createMenuItem)
        .onEvent(RestaurantMenuItemRevisedEvent.class, this::reviseMenuItem)
        .onEvent(RestaurantMenuItemDeletedEvent.class, this::deleteMenuItem)
        .build();
  }


  private Mono<Void> createRestaurant(DomainEventEnvelope<RestaurantCreatedEvent> de) {
    return Mono.defer(() -> {
          String restaurantId = de.getAggregateId();
          log.info("Handling RestaurantCreatedEvent for restaurant with ID {}", restaurantId);

          Restaurant restaurant = restaurantMapper.restaurantCreatedEventToRestaurant(restaurantId,
              de.getEvent());

          return restaurantService.createRestaurant(restaurant)
              .doOnSuccess(r -> log.info("Restaurant with ID {} created", r.getId()))
              .then();
        })
        .onErrorResume(e -> {
          log.error("Error creating restaurant with ID {}: {}", de.getAggregateId(),
              e.getMessage(), e);
          return Mono.error(e);
        });
  }

  private Mono<Void> reviseRestaurant(DomainEventEnvelope<RestaurantRevisedEvent> de) {
    return Mono.defer(() -> {
          String restaurantId = de.getAggregateId();
          log.info("Handling RestaurantRevisedEvent for restaurant with ID {}", restaurantId);

          return restaurantService.reviseRestaurant(restaurantId, de.getEvent())
              .doOnSuccess(r -> log.info("Restaurant with ID {} revised", r.getId()))
              .then();
        })
        .onErrorResume(e -> {
          log.error("Error revising restaurant with ID {}: {}", de.getAggregateId(),
              e.getMessage(), e);
//          return Mono.error(e);
          return Mono.empty();
        });
  }

  private Mono<Void> deleteRestaurant(
      DomainEventEnvelope<RestaurantDeletedEvent> de) {
    return Mono.defer(() -> {
          String restaurantId = de.getAggregateId();
          log.info("Handling RestaurantDeletedEvent for restaurant with ID {}", restaurantId);

          return restaurantService.deleteRestaurant(restaurantId)
              .doOnSuccess(r -> log.info("Restaurant with ID {} was deleted", restaurantId))
              .then();
        })
        .onErrorResume(e -> {
          log.error("Error deleting restaurant with ID {}: {}", de.getAggregateId(),
              e.getMessage(), e);
//          return Mono.error(e);
          return Mono.empty();
        });
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
//      return Mono.error(e);
      return Mono.empty();
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
//      return Mono.error(e);
      return Mono.empty();
    });
  }

  private Mono<Void> deleteMenuItem(DomainEventEnvelope<RestaurantMenuItemDeletedEvent> de) {
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
//      return Mono.error(e);
      return Mono.empty();
    });
  }
}
