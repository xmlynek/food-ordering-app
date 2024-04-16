package com.food.ordering.app.payment.service.aggregate;

import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.common.event.PaymentSuccessEvent;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {

  @AggregateIdentifier
  private UUID paymentId;

//  @Autowired
//  KafkaConsumer<String, byte[]> kafkaConsumer;

//  private UUID orderId;
//  private UUID customerId;
//  private BigDecimal price;
//  private PaymentStatus paymentStatus;

  @CommandHandler
  public PaymentAggregate(ProcessPaymentCommand command) {
    log.info("Handling ProcessPaymentCommand for orderId {}", command.getOrderId().toString());
    PaymentSuccessEvent paymentSuccessEvent = PaymentSuccessEvent.builder()
        .paymentId(command.getPaymentId())
        .customerId(command.getCustomerId())
        .orderId(command.getOrderId())
        .price(command.getPrice())
        .build();

//    System.out.println(kafkaConsumer.listTopics());
    AggregateLifecycle.apply(paymentSuccessEvent);
  }

  @EventSourcingHandler
  public void handle(PaymentSuccessEvent event) {
    try {
      Thread.sleep(5 * 1000);
      log.info("Handling PaymentSuccessEvent for payment {}", event.getPaymentId().toString());

      this.paymentId = event.getPaymentId();
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }


//    this.orderId = event.getOrderId();
//    this.customerId = event.getCustomerId();
//    this.price = event.getPrice();
//    this.paymentStatus = PaymentStatus.COMPLETED;
  }

}
