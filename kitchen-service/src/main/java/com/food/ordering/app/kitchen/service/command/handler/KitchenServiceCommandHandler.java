package com.food.ordering.app.kitchen.service.command.handler;

import com.food.ordering.app.common.command.CreateKitchenTicketCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

public abstract class KitchenServiceCommandHandler {

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
        .fromChannel("kitchen-service")
        .onMessage(CreateKitchenTicketCommand.class, this::createTicket)
        .build();
  }

  protected abstract Message createTicket(CommandMessage<CreateKitchenTicketCommand> cm);
}
