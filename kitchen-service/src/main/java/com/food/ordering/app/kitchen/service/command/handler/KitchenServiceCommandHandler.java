package com.food.ordering.app.kitchen.service.command.handler;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import com.food.ordering.app.kitchen.service.config.properties.SagaCommandHandlerProperties;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class KitchenServiceCommandHandler {

  private final SagaCommandHandlerProperties sagaCommandHandlerProperties;

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
        .fromChannel(sagaCommandHandlerProperties.getChannel())
        .onMessage(CreateKitchenTicketCommand.class, this::createTicket)
        .build();
  }

  protected abstract Message createTicket(CommandMessage<CreateKitchenTicketCommand> cm);
}
