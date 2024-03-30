package com.food.ordering.app.delivery.service.config;

import com.food.ordering.app.delivery.service.command.handler.DeliveryServiceCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfig {

  public static final String DELIVERY_SERVICE_COMMAND_DISPATCHER_NAME = "deliveryServiceCommandDispatcher";

  @Bean
  public CommandDispatcher consumerCommandDispatcher(DeliveryServiceCommandHandler target,
      SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
    return sagaCommandDispatcherFactory.make(DELIVERY_SERVICE_COMMAND_DISPATCHER_NAME,
        target.commandHandlerDefinitions());
  }
}
