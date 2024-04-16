package com.food.ordering.app.order.service.saga.proxy;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.food.ordering.app.common.command.CancelPaymentCommand;
import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.order.service.config.properties.CommandDestinationProperties;
import io.eventuate.examples.common.money.Money;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceProxy {

  private final CommandDestinationProperties commandDestinationProperties;

  public CommandWithDestination processPayment(UUID orderId, UUID customerId, Money orderTotal,
      String paymentToken) {
    return send(new ProcessPaymentCommand(orderId, customerId, orderTotal, paymentToken))
        .to(commandDestinationProperties.getPaymentService())
        .build();
  }

  public CommandWithDestination cancelPayment(UUID paymentId, UUID orderId, UUID customerId,
      Money orderTotal) {
    return send(new CancelPaymentCommand(paymentId, orderId, customerId, orderTotal))
        .to(commandDestinationProperties.getPaymentService())
        .build();
  }
}
