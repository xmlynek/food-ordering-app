package com.food.ordering.app.restaurant.service.config;

import com.food.ordering.app.restaurant.service.command.handler.RestaurantCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfig {

  public static final String RESTAURANT_COMMAND_DISPATCHER_NAME = "restaurantCommandDispatcher";

  @Bean
  public CommandDispatcher consumerCommandDispatcher(RestaurantCommandHandler target,
      SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
    return sagaCommandDispatcherFactory.make(RESTAURANT_COMMAND_DISPATCHER_NAME,
        target.commandHandlerDefinitions());
  }
}
