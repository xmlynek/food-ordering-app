package com.food.ordering.app.delivery.service.config;

import com.food.ordering.app.delivery.service.event.handler.DeliveryServiceEventHandler;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerConfig {

  @Bean
  public DomainEventDispatcher restaurantDomainEventDispatcher(
      DomainEventDispatcherFactory domainEventDispatcherFactory,
      DeliveryServiceEventHandler deliveryServiceEventHandler) {
    return domainEventDispatcherFactory.make("deliveryServiceEvents",
        deliveryServiceEventHandler.domainEventHandlers());
  }

}
