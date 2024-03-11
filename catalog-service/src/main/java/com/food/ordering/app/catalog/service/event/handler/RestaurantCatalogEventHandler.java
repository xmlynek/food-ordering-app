package com.food.ordering.app.catalog.service.event.handler;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.catalog.service.mapper.RestaurantMapper;
import com.food.ordering.app.catalog.service.service.RestaurantService;
import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantDeletedEvent;
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
public class RestaurantCatalogEventHandler {

  private final RestaurantMapper restaurantMapper;
  private final RestaurantService restaurantService;


  public ReactiveDomainEventHandlers domainEventHandlers() {
    return ReactiveDomainEventHandlersBuilder
        .forAggregateType("com.food.ordering.app.restaurant.service.entity.Restaurant")
        .onEvent(RestaurantCreatedEvent.class, this::createRestaurant)
        .onEvent(RestaurantRevisedEvent.class, this::reviseRestaurant)
        .onEvent(RestaurantDeletedEvent.class, this::deleteRestaurant)
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
          return Mono.error(e);
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
          return Mono.error(e);
        });
  }
}
