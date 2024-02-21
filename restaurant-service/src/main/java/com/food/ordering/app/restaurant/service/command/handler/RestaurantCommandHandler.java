package com.food.ordering.app.restaurant.service.command.handler;

import com.food.ordering.app.common.command.ApproveOrderCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

public abstract class RestaurantCommandHandler {

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
        .fromChannel("restaurant-service")
        .onMessage(ApproveOrderCommand.class, this::approveOrder)
//        .onMessage(CancelPaymentCommand.class, this::cancelPayment)
        .build();
  }

//  protected abstract Message cancelOrderApproval(CommandMessage<ApproveOrderCommand> cm);

  protected abstract Message approveOrder(CommandMessage<ApproveOrderCommand> cm);
}
