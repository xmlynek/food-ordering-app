package com.food.ordering.app.payment.service.config;

import com.food.ordering.app.payment.service.command.handler.PaymentCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandHandlerConfig {

  @Bean
  public CommandDispatcher consumerCommandDispatcher(PaymentCommandHandler target,
      SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

    return sagaCommandDispatcherFactory.make("paymentCommandDispatcher",
        target.commandHandlerDefinitions());
  }
}
