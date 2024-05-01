package com.food.ordering.app.catalog.service.config;

import com.food.ordering.app.catalog.service.event.handler.CatalogServiceEventHandler;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventDispatcher;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerConfig {

  @Bean
  public ReactiveDomainEventDispatcher restaurantDomainEventDispatcher(
      ReactiveDomainEventDispatcherFactory reactiveDomainEventDispatcherFactory,
      CatalogServiceEventHandler catalogServiceEventHandler) {
    return reactiveDomainEventDispatcherFactory.make("CatalogServiceEvents",
        catalogServiceEventHandler.domainEventHandlers());
  }
}
