package com.food.ordering.app.delivery.service.event.publisher;

import com.food.ordering.app.common.event.DeliveryDomainEvent;
import com.food.ordering.app.delivery.service.entity.Delivery;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DeliveryDomainEventPublisher extends
    AbstractAggregateDomainEventPublisher<Delivery, DeliveryDomainEvent> {

  public DeliveryDomainEventPublisher(DomainEventPublisher eventPublisher) {
    super(eventPublisher, Delivery.class, Delivery::getStringId);
  }
}
