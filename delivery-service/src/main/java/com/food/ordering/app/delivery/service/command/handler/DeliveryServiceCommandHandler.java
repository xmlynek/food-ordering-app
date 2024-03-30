package com.food.ordering.app.delivery.service.command.handler;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.delivery.service.config.properties.CommandHandlerProperties;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DeliveryServiceCommandHandler {

  private final CommandHandlerProperties commandHandlerProperties;

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
        .fromChannel(commandHandlerProperties.getChannel())
        .onMessage(PrepareOrderDeliveryCommand.class, this::prepareOrderDelivery)
        .build();
  }

  protected abstract void prepareOrderDelivery(CommandMessage<PrepareOrderDeliveryCommand> cm);
}
