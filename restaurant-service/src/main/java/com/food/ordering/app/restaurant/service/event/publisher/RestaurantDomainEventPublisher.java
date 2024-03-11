package com.food.ordering.app.restaurant.service.event.publisher;

import com.food.ordering.app.common.event.RestaurantDomainEvent;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantDomainEventPublisher extends
    AbstractAggregateDomainEventPublisher<Restaurant, RestaurantDomainEvent> {

  public RestaurantDomainEventPublisher(DomainEventPublisher eventPublisher) {
    super(eventPublisher, Restaurant.class, Restaurant::getStringId);
  }
}
