package com.food.ordering.app.order.service.saga;

import com.food.ordering.app.common.response.kitchen.CreateKitchenTicketFailed;
import com.food.ordering.app.common.response.kitchen.KitchenTicketCreated;
import com.food.ordering.app.common.response.payment.ProcessPaymentFailed;
import com.food.ordering.app.common.response.payment.ProcessPaymentSucceeded;
import com.food.ordering.app.order.service.entity.OrderStatus;
import com.food.ordering.app.order.service.saga.data.CreateOrderSagaData;
import com.food.ordering.app.order.service.saga.proxy.DeliveryServiceProxy;
import com.food.ordering.app.order.service.saga.proxy.KitchenServiceProxy;
import com.food.ordering.app.order.service.saga.proxy.PaymentServiceProxy;
import com.food.ordering.app.order.service.service.OrderService;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

  private final OrderService orderService;
  private final PaymentServiceProxy paymentServiceProxy;
  private final KitchenServiceProxy kitchenServiceProxy;
  private final DeliveryServiceProxy deliveryServiceProxy;

  private final SagaDefinition<CreateOrderSagaData> sagaDefinition =
      step()
        .invokeLocal(this::createOrder)
        .withCompensation(this::cancelOrder)
      .step()
        .invokeParticipant(this::processPayment)
        .onReply(ProcessPaymentSucceeded.class, this::handleProcessPaymentSucceeded)
        .onReply(ProcessPaymentFailed.class, this::handleProcessPaymentFailed)
        .withCompensation(this::cancelPayment)
      .step()
        .invokeParticipant(this::createKitchenTicket)
        .onReply(KitchenTicketCreated.class, this::handleCreateKitchenTicketSucceeded)
        .onReply(CreateKitchenTicketFailed.class, this::handleCreateKitchenTicketFailed)
      .step()
          .notifyParticipant(this::notifyDeliveryService)
      .step()
        .invokeLocal(this::confirmOrder)
      .build();


  @Override
  public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }


  private void createOrder(CreateOrderSagaData sagaData) {
    log.info("Create order saga started for order id: {}", sagaData.getOrderId().toString());
  }

  private void cancelOrder(CreateOrderSagaData data) {
    log.info("CreateOrderSaga cancelled for order id: {}", data.getOrderId().toString());
    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.CANCELLED);
    orderService.setFailureMessages(data.getOrderId(), data.getFailureMessages());
  }

  private void confirmOrder(CreateOrderSagaData data) {
    log.info("Order: {} was confirmed and approved and kitchen ticket created.", data.getOrderId().toString());
    // TODO: maybe different status?
//    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.APPROVED);
  }

  private void handleCreateKitchenTicketSucceeded(CreateOrderSagaData data,
      KitchenTicketCreated kitchenTicketCreated) {
    data.setTicketId(kitchenTicketCreated.ticketId());
    log.info("Kitchen ticket successfully created with id: {} for order id: {}",
        kitchenTicketCreated.ticketId().toString(), data.getOrderId().toString());
    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.APPROVED);
    orderService.setTicketId(data.getOrderId(), kitchenTicketCreated.ticketId());
  }

  private void handleCreateKitchenTicketFailed(CreateOrderSagaData data,
      CreateKitchenTicketFailed kitchenTicketFailed) {
    log.info("Kitchen ticket creation FAILED with for order id: {} with failure message: {}",
        data.getOrderId().toString(), kitchenTicketFailed.failureMessage());
    data.getFailureMessages().add(kitchenTicketFailed.failureMessage());
    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.CANCELLING);
  }

  private void handleProcessPaymentSucceeded(CreateOrderSagaData data,
      ProcessPaymentSucceeded response) {
    data.setPaymentId(response.paymentId());
    log.info("Process payment succeeded with for order id: {}, with payment id: {}",
        data.getOrderId().toString(), response.paymentId().toString());
    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.PAID);
  }

  private void handleProcessPaymentFailed(CreateOrderSagaData data,
      ProcessPaymentFailed processPaymentFailed) {
    log.info("Process payment failed for order id: {} with failure message: {}",
        data.getOrderId().toString(), processPaymentFailed.failureMessage());
    data.getFailureMessages().add(processPaymentFailed.failureMessage());
    orderService.updateOrderStatus(data.getOrderId(), OrderStatus.CANCELLING);
  }

  private CommandWithDestination cancelPayment(CreateOrderSagaData data) {
    log.info("Cancel payment compensation saga step started for order {} and payment {}",
        data.getOrderId().toString(), data.getPaymentId().toString());
    return paymentServiceProxy.cancelPayment(data.getPaymentId(), data.getOrderId(),
        data.getCustomerId(), data.getTotalPrice());
  }

  private CommandWithDestination processPayment(CreateOrderSagaData data) {
    log.info("Process payment started for order id: {}", data.getOrderId().toString());
    return paymentServiceProxy.processPayment(data.getOrderId(), data.getCustomerId(),
        data.getTotalPrice(), data.getPaymentToken());
  }

  private CommandWithDestination createKitchenTicket(CreateOrderSagaData data) {
    log.info("Create kitchen ticket saga step started for order id: {}",
        data.getOrderId().toString());
    return kitchenServiceProxy.createKitchenTicket(data.getOrderId(), data.getCustomerId(),
        data.getRestaurantId(), data.getItems());
  }

  private CommandWithDestination notifyDeliveryService(CreateOrderSagaData data) {
    log.info("Notifying delivery service with order id: {}", data.getOrderId().toString());
    return deliveryServiceProxy.notifyDeliveryService(data.getOrderId(), data.getCustomerId(),
        data.getRestaurantId(), data.getAddress());
  }

}
