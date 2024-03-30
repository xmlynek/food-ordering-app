package com.food.ordering.app.payment.service.command.handler;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.common.response.payment.ProcessPaymentFailed;
import com.food.ordering.app.common.response.payment.ProcessPaymentSucceeded;
import com.food.ordering.app.payment.service.config.properties.SagaCommandHandlerProperties;
import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.mapper.PaymentMapper;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentRequest;
import com.food.ordering.app.payment.service.payment.model.dto.PaymentResult;
import com.food.ordering.app.payment.service.payment.model.dto.RefundResult;
import com.food.ordering.app.payment.service.payment.model.enums.Currency;
import com.food.ordering.app.payment.service.payment.strategy.PaymentStrategy;
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
  private final PaymentMapper paymentMapper;
  private final PaymentStrategy paymentStrategy;

  public PaymentCommandHandlerImpl(SagaCommandHandlerProperties sagaCommandHandlerProperties,
      PaymentService paymentService, PaymentMapper paymentMapper, PaymentStrategy paymentStrategy) {
    super(sagaCommandHandlerProperties);
    this.paymentService = paymentService;
    this.paymentMapper = paymentMapper;
    this.paymentStrategy = paymentStrategy;
  }

  @Override
  @Transactional
  public Message processPayment(CommandMessage<ProcessPaymentCommand> cm) {
    ProcessPaymentCommand command = cm.getCommand();
    log.info("Process payment started for order id {}", command.orderId().toString());

    try {
      Payment payment = paymentService.savePayment(
          paymentMapper.paymentRequestToPaymentEntity(command));

      PaymentResult paymentResult = paymentStrategy.processPayment(
          new PaymentRequest(Currency.EUR, command.amount(), "Food ordering app test payment",
              command.paymentToken()));

      payment.setChargeId(paymentResult.paymentId());
      payment.setPaymentStatus(PaymentStatus.COMPLETED);
      payment = paymentService.savePayment(payment);

      log.info("Payment {} finished with status {}, chargeID {}", payment.getId().toString(),
          paymentResult.status(), paymentResult.paymentId());

      return CommandHandlerReplyBuilder.withSuccess(
          new ProcessPaymentSucceeded(payment.getId(), command.orderId()));
    } catch (Exception e) {
      log.error("Creation of payment failed. {}", e.getMessage());
      return CommandHandlerReplyBuilder.withFailure(
          new ProcessPaymentFailed(command.orderId(), command.customerId(), e.getMessage()));
    }
  }

  @Override
  @Transactional
  public Message cancelPayment(CommandMessage<CancelPaymentCommand> cm) {
    CancelPaymentCommand command = cm.getCommand();
    log.info("Payment compensation started for payment id: {}, and order id {}",
        command.paymentId().toString(), command.orderId().toString());

    try {
      Payment payment = paymentService.getPaymentById(command.paymentId());

      RefundResult refundResult = paymentStrategy.refundPayment(payment.getChargeId());

      paymentService.updateStatus(command.paymentId(), PaymentStatus.CANCELLED);
      log.info("Payment with id {} was cancelled and refunded with id {} and status: {}",
          command.paymentId(), refundResult.refundId(), refundResult.status());
      return CommandHandlerReplyBuilder.withSuccess();
    } catch (Exception e) {
      log.error("Payment compensation failed. {}", e.getMessage());
      return CommandHandlerReplyBuilder.withFailure();
    }
  }
}
