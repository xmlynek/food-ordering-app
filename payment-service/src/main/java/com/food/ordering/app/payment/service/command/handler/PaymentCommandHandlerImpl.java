package com.food.ordering.app.payment.service.command.handler;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.enums.PaymentStatus;
import com.food.ordering.app.common.response.payment.ProcessPaymentFailed;
import com.food.ordering.app.common.response.payment.ProcessPaymentSucceeded;
import com.food.ordering.app.payment.service.entity.Payment;
import com.food.ordering.app.payment.service.mapper.PaymentMapper;
import com.food.ordering.app.payment.service.service.PaymentService;
import com.stripe.Stripe;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCommandHandlerImpl extends PaymentCommandHandler {

  private final PaymentService paymentService;
  private final PaymentMapper paymentMapper;

  @PostConstruct
  public void init() {
    Stripe.apiKey = "sk_test_51Oc4G2Hg2RuOlHnDmj9OJ8NPORZpLpqHPg9kJN0msWpiTS9d3kfDj86sOoMyIBnppg0dGN80LyUODIzDZrn9xJDO00Lco5Q5f1";
  }


  @Override
  @Transactional
  public Message processPayment(CommandMessage<ProcessPaymentCommand> cm) {
    ProcessPaymentCommand command = cm.getCommand();
    log.info("Process payment started for order id {}", command.orderId().toString());

    try {
      Payment payment = paymentService.createPayment(
          paymentMapper.paymentRequestToPaymentEntity(command));
      log.info("Created payment {}", payment.getId().toString());
//
//      Map<String, Object> chargeParams = new HashMap<>();
//      chargeParams.put("amount",
//          Integer.parseInt(command.amount().getAmount().toString().replace(".", "")));
//      chargeParams.put("currency", "EUR");
//      chargeParams.put("description", "Food ordering app test payment");
//      chargeParams.put("source", command.paymentToken());
//      Charge charge = Charge.create(chargeParams);
//
//      paymentService.updateStatus(payment.getId(), PaymentStatus.COMPLETED);
//      log.info("Payment {} payment succeeded, chargeID {}", payment.getId().toString(),
//          charge.getId());

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
      paymentService.updateStatus(command.paymentId(), PaymentStatus.CANCELLED);
      log.info("Payment with id {} was cancelled", command.paymentId().toString());
      return CommandHandlerReplyBuilder.withSuccess();
    } catch (Exception e) {
      log.error("Payment compensation failed. {}", e.getMessage());
//      return CommandHandlerReplyBuilder.withFailure(
//          new ProcessPaymentFailed(command.orderId(), command.customerId(), e.getMessage()));
    }
    return CommandHandlerReplyBuilder.withSuccess();
//    return null;
  }
}
