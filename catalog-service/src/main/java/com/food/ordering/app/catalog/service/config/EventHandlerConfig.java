package com.food.ordering.app.catalog.service.config;

import com.food.ordering.app.catalog.service.event.handler.RestaurantCatalogEventHandler;
import com.food.ordering.app.catalog.service.event.handler.RestaurantMenuItemCatalogEventHandler;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventDispatcher;
import io.eventuate.tram.reactive.events.subscriber.ReactiveDomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerConfig {

  @Bean
  public ReactiveDomainEventDispatcher restaurantDomainEventDispatcher(
      ReactiveDomainEventDispatcherFactory reactiveDomainEventDispatcherFactory,
      RestaurantCatalogEventHandler restaurantCatalogEventHandler) {
    return reactiveDomainEventDispatcherFactory.make("restaurantCatalogServiceEvents",
        restaurantCatalogEventHandler.domainEventHandlers());
  }

  @Bean
  public ReactiveDomainEventDispatcher restaurantMenuItemDomainEventDispatcher(
      ReactiveDomainEventDispatcherFactory reactiveDomainEventDispatcherFactory,
      RestaurantMenuItemCatalogEventHandler restaurantMenuItemCatalogEventHandler) {
    return reactiveDomainEventDispatcherFactory.make("restaurantMenuItemCatalogServiceEvents",
        restaurantMenuItemCatalogEventHandler.domainEventHandlers());
  }

}
