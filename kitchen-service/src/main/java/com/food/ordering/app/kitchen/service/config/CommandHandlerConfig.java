package com.food.ordering.app.kitchen.service.config;

import com.food.ordering.app.kitchen.service.command.handler.KitchenServiceCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfig {

  public static final String KITCHEN_SERVICE_COMMAND_DISPATCHER_NAME = "kitchenServiceCommandDispatcher";

  @Bean
  public CommandDispatcher consumerCommandDispatcher(KitchenServiceCommandHandler target,
      SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
    return sagaCommandDispatcherFactory.make(KITCHEN_SERVICE_COMMAND_DISPATCHER_NAME,
        target.commandHandlerDefinitions());
  }
}
