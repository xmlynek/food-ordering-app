package com.food.ordering.app.restaurant.service.event.publisher;

import com.food.ordering.app.common.event.RestaurantMenuItemDomainEvent;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuItemDomainEventPublisher extends
    AbstractAggregateDomainEventPublisher<MenuItem, RestaurantMenuItemDomainEvent> {

  public RestaurantMenuItemDomainEventPublisher(DomainEventPublisher eventPublisher) {
    super(eventPublisher, MenuItem.class, MenuItem::getStringId);
  }
}
