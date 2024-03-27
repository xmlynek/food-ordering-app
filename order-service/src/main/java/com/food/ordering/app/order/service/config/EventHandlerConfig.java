package com.food.ordering.app.order.service.config;

import com.food.ordering.app.order.service.event.handler.OrderServiceEventHandler;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerConfig {

  @Bean
  public DomainEventDispatcher restaurantDomainEventDispatcher(
      DomainEventDispatcherFactory reactiveDomainEventDispatcherFactory,
      OrderServiceEventHandler orderServiceEventHandler) {
    return reactiveDomainEventDispatcherFactory.make("orderServiceEvents",
        orderServiceEventHandler.domainEventHandlers());
  }

}
