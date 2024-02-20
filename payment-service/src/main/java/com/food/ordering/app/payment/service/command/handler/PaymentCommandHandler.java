package com.food.ordering.app.payment.service.command.handler;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

public abstract class PaymentCommandHandler {

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
        .fromChannel("payment-service")
        .onMessage(ProcessPaymentCommand.class, this::processPayment)
        .onMessage(CancelPaymentCommand.class, this::cancelPayment)
        .build();
  }

  protected abstract Message cancelPayment(CommandMessage<CancelPaymentCommand> cm);

  protected abstract Message processPayment(CommandMessage<ProcessPaymentCommand> cm);
}
