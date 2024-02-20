package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import io.eventuate.examples.common.money.Money;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceProxy {

  public CommandWithDestination processPayment(UUID orderId, UUID customerId, Money orderTotal) {
    return send(new ProcessPaymentCommand(orderId, customerId, orderTotal))
        // TODO: add to config
        .to("payment-service")
        .build();
  }

  public CommandWithDestination cancelPayment(UUID paymentId, UUID orderId, UUID customerId, Money orderTotal) {
    return send(new CancelPaymentCommand(paymentId, orderId, customerId, orderTotal))
        // TODO: add to config
        .to("payment-service")
        .build();
  }
}
