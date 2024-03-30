package com.food.ordering.app.kitchen.service.config;

import com.food.ordering.app.kitchen.service.event.handler.KitchenServiceEventHandler;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerConfig {

  @Bean
  public DomainEventDispatcher restaurantDomainEventDispatcher(
      DomainEventDispatcherFactory domainEventDispatcherFactory,
      KitchenServiceEventHandler kitchenServiceEventHandler) {
    return domainEventDispatcherFactory.make("kitchenServiceEvents",
        kitchenServiceEventHandler.domainEventHandlers());
  }

}
