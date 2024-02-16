package com.food.ordering.app.order.service.saga;

import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.event.OrderCreatedEvent;
import com.food.ordering.app.common.event.PaymentSuccessEvent;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@NoArgsConstructor
@Slf4j
public class CreateOrderSaga {

  @Autowired
  private CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  private void handle(OrderCreatedEvent event) {
    log.info("Saga handling OrderCreatedEvent for order {}", event.getOrderId());

    UUID paymentId = UUID.randomUUID();
    //Associate the payment ID
    SagaLifecycle.associateWith("paymentId", paymentId.toString());

    //Create a command to make payment
    ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
        .orderId(event.getOrderId())
        .customerId(event.getCustomerId())
        .paymentId(paymentId)
        .price(event.getPrice())
        .build();
    commandGateway.send(processPaymentCommand);
    log.info("Saga send ProcessPaymentCommand with paymentId {} for order {}", paymentId,
        event.getOrderId());
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "paymentId")
  private void handle(PaymentSuccessEvent event) {
    log.info("Saga handle PaymentSuccessEvent for paymentId {} and order {}", event.getPaymentId(),
        event.getOrderId());
  }
}
