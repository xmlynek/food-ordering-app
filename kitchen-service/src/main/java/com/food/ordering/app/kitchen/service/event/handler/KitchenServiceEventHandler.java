package com.food.ordering.app.kitchen.service.event.handler;

import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.kitchen.service.entity.MenuItem;
import com.food.ordering.app.kitchen.service.entity.Restaurant;
import com.food.ordering.app.kitchen.service.mapper.MenuItemMapper;
import com.food.ordering.app.kitchen.service.mapper.RestaurantMapper;
import com.food.ordering.app.kitchen.service.service.RestaurantMenuItemService;
import com.food.ordering.app.kitchen.service.service.RestaurantService;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KitchenServiceEventHandler {

  private final RestaurantMapper restaurantMapper;
  private final MenuItemMapper menuItemMapper;
  private final RestaurantService restaurantService;
  private final RestaurantMenuItemService menuItemService;


  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder.forAggregateType(
            "com.food.ordering.app.restaurant.service.entity.Restaurant")
        .onEvent(RestaurantCreatedEvent.class, this::createRestaurant)
        .onEvent(RestaurantRevisedEvent.class, this::reviseRestaurant)
        .onEvent(RestaurantDeletedEvent.class, this::deleteRestaurant)
        .andForAggregateType("com.food.ordering.app.restaurant.service.entity.MenuItem")
        .onEvent(RestaurantMenuItemCreatedEvent.class, this::createMenuItem)
        .onEvent(RestaurantMenuItemRevisedEvent.class, this::reviseMenuItem)
        .onEvent(RestaurantMenuItemDeletedEvent.class, this::deleteMenuItem).build();
  }


  private void createRestaurant(DomainEventEnvelope<RestaurantCreatedEvent> de) {
    try {
      UUID restaurantId = UUID.fromString(de.getAggregateId());
      log.info("Handling RestaurantCreatedEvent for restaurant with ID {}", restaurantId);

      Restaurant restaurant = restaurantMapper.restaurantCreatedEventToRestaurant(restaurantId,
          de.getEvent());

      Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
      log.info("Restaurant with ID {} created", createdRestaurant.getId());
    } catch (Exception e) {
      log.error("Error creating restaurant with ID {}: {}", de.getAggregateId(), e.getMessage(), e);
//      throw e;
    }
  }

  private void reviseRestaurant(DomainEventEnvelope<RestaurantRevisedEvent> de) {
    try {
      UUID restaurantId = UUID.fromString(de.getAggregateId());
      log.info("Handling RestaurantRevisedEvent for restaurant with ID {}", restaurantId);

      Restaurant revisedRestaurant = restaurantService.reviseRestaurant(restaurantId,
          de.getEvent());
      log.info("Restaurant with ID {} revised", revisedRestaurant.getId());
    } catch (Exception e) {
      log.error("Error revising restaurant with ID {}: {}", de.getAggregateId(), e.getMessage(), e);
//      throw e;
    }
  }

  private void deleteRestaurant(DomainEventEnvelope<RestaurantDeletedEvent> de) {
    try {
      UUID restaurantId = UUID.fromString(de.getAggregateId());
      log.info("Handling RestaurantDeletedEvent for restaurant with ID {}", restaurantId);

      restaurantService.deleteRestaurant(restaurantId);
      log.info("Restaurant with ID {} was deleted", restaurantId);
    } catch (Exception e) {
      log.error("Error deleting restaurant with ID {}: {}", de.getAggregateId(), e.getMessage(), e);
//      throw e;
    }
  }

  private void createMenuItem(DomainEventEnvelope<RestaurantMenuItemCreatedEvent> de) {
    UUID menuItemId = UUID.fromString(de.getAggregateId());
    RestaurantMenuItemCreatedEvent event = de.getEvent();
    UUID restaurantId = UUID.fromString(event.restaurantId());
    try {
      log.info(
          "Handling RestaurantMenuItemCreatedEvent for restaurant with ID {} and menu item with ID {}",
          restaurantId, menuItemId);

      MenuItem menuItem = menuItemMapper.restaurantMenuItemCreatedEvent(menuItemId, event);
      MenuItem createdMenuItem = menuItemService.addMenuItem(restaurantId, menuItem);

      log.info("MenuItem with ID {} in restaurant with ID {} created", createdMenuItem.getId(),
          restaurantId);
    } catch (Exception e) {
      log.error("Error creating menu item with ID {} in restaurant with ID {}: {}", menuItemId,
          restaurantId, e.getMessage(), e);
//      throw e;
    }
  }

  public void reviseMenuItem(DomainEventEnvelope<RestaurantMenuItemRevisedEvent> de) {
    UUID menuItemId = UUID.fromString(de.getAggregateId());
    RestaurantMenuItemRevisedEvent event = de.getEvent();
    String restaurantId = event.restaurantId();
    try {
      log.info(
          "Handling RestaurantMenuItemRevisedEvent for restaurant with ID {} and menu item with ID {}",
          restaurantId, menuItemId);

      MenuItem revisedMenuItem = menuItemService.reviseMenuItem(menuItemId, event);

      log.info("MenuItem with ID {} in restaurant with ID {} revised", revisedMenuItem.getId(),
          restaurantId);
    } catch (Exception e) {
      log.error("Error revising menu item with ID {} in restaurant with ID {}: {}", menuItemId,
          restaurantId, e.getMessage(), e);
//      throw e;
    }
  }

  private void deleteMenuItem(DomainEventEnvelope<RestaurantMenuItemDeletedEvent> de) {
      UUID menuItemId = UUID.fromString(de.getAggregateId());
      String restaurantId = de.getEvent().restaurantId();
    try {
      log.info(
          "Handling RestaurantMenuItemDeletedEvent for menu item with ID {} in restaurant with ID {}",
          menuItemId, restaurantId);

      menuItemService.deleteMenuItem(menuItemId);
      log.info("MenuItem with ID {} in restaurant with ID {} was deleted", menuItemId,
          restaurantId);
    } catch (Exception e) {
      log.error("Error deleting menu item with ID {} in restaurant with ID {}: {}", menuItemId,
          restaurantId, e.getMessage(), e);
//      throw e;
    }
  }
}
