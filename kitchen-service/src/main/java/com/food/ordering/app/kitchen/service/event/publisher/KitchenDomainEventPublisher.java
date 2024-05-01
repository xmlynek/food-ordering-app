package com.food.ordering.app.kitchen.service.event.publisher;

import com.food.ordering.app.common.event.KitchenDomainEvent;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class KitchenDomainEventPublisher extends
    AbstractAggregateDomainEventPublisher<KitchenTicket, KitchenDomainEvent> {

  public KitchenDomainEventPublisher(DomainEventPublisher eventPublisher) {
    super(eventPublisher, KitchenTicket.class, KitchenTicket::getStringId);
  }
}
