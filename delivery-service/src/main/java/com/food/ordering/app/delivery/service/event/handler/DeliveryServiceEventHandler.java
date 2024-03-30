package com.food.ordering.app.delivery.service.event.handler;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.KitchenTicketStatusChangedEvent;
import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantDeletedEvent;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.delivery.service.entity.Restaurant;
import com.food.ordering.app.delivery.service.mapper.RestaurantMapper;
import com.food.ordering.app.delivery.service.service.RestaurantService;
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
public class DeliveryServiceEventHandler {

  private final RestaurantMapper restaurantMapper;
  private final RestaurantService restaurantService;


  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder.forAggregateType(
            "com.food.ordering.app.kitchen.service.entity.KitchenTicket")
//        .onEvent(KitchenTicketStatusChangedEvent.class, this::handleKitchenTicketStatusChangedEvent)
        .andForAggregateType("com.food.ordering.app.restaurant.service.entity.Restaurant")
        .onEvent(RestaurantCreatedEvent.class, this::createRestaurant)
        .onEvent(RestaurantRevisedEvent.class, this::reviseRestaurant)
        .onEvent(RestaurantDeletedEvent.class, this::deleteRestaurant)
        .build();
  }


  private void handleKitchenTicketStatusChangedEvent(
      DomainEventEnvelope<KitchenTicketStatusChangedEvent> de) {
    try {
      UUID ticketId = UUID.fromString(de.getAggregateId());
      KitchenTicketStatusChangedEvent event = de.getEvent();
      KitchenTicketStatus status = event.status();
      log.info(
          "Handling KitchenTicketStatusChangedEvent with status {} for order with ticket ID {}",
          status, ticketId);
// TODO: update
//      orderService.updateKitchenTicketStatus(ticketId, status);

      log.info(
          "Successfully handled KitchenTicketStatusChangedEvent for order with ticket ID {} and status {}",
          ticketId, status);
    } catch (Exception e) {
      log.error("Error handling KitchenTicketStatusChangedEvent for order with ticket ID {}: {}",
          de.getAggregateId(), e.getMessage(), e);
    }
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
}
