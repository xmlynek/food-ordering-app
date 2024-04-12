package com.food.ordering.app.payment.service.command.handler;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.common.response.payment.ProcessPaymentFailed;
import com.food.ordering.app.common.response.payment.ProcessPaymentSucceeded;
import com.food.ordering.app.payment.service.config.properties.SagaCommandHandlerProperties;
import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.service.PaymentService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PaymentCommandHandlerImpl extends PaymentCommandHandler {

  private final PaymentService paymentService;

  public PaymentCommandHandlerImpl(SagaCommandHandlerProperties sagaCommandHandlerProperties,
      PaymentService paymentService) {
    super(sagaCommandHandlerProperties);
    this.paymentService = paymentService;
  }

  @Override
  @Transactional
  public Message processPayment(CommandMessage<ProcessPaymentCommand> cm) {
    ProcessPaymentCommand command = cm.getCommand();
    log.info("Process payment started for order id {}", command.orderId().toString());
    Payment payment;

    try {
      payment = paymentService.processPayment(command);

      log.info("Payment {} was processed and finished with status {}", payment.getId().toString(),
          payment.getPaymentStatus().toString());

      return CommandHandlerReplyBuilder.withSuccess(
          new ProcessPaymentSucceeded(payment.getId(), command.orderId(),
              payment.getPaymentStatus()));
    } catch (Exception e) {
      log.error("Creation of payment failed. {}", e.getMessage());
      payment = paymentService.saveFailedPayment(command);
      log.error("Failed payment was saved with {} status", payment.getPaymentStatus().toString());
      return CommandHandlerReplyBuilder.withFailure(
          new ProcessPaymentFailed(command.orderId(), command.customerId(), PaymentStatus.FAILED,
              e.getMessage()));
    }
  }

  @Override
  @Transactional
  public Message cancelPayment(CommandMessage<CancelPaymentCommand> cm) {
    CancelPaymentCommand command = cm.getCommand();
    log.info("Payment compensation started for payment id: {}, and order id {}",
        command.paymentId().toString(), command.orderId().toString());

    try {
      Payment payment = paymentService.refundPayment(command);

      log.info("Payment with id {} was cancelled and refunded with status: {}", command.paymentId(),
          payment.getPaymentStatus());
      return CommandHandlerReplyBuilder.withSuccess();
    } catch (Exception e) {
      log.error("Payment compensation failed. {}", e.getMessage());
      return CommandHandlerReplyBuilder.withFailure();
    }
  }
}
