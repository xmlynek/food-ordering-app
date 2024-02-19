package com.food.ordering.app.order.service.saga;

import com.food.ordering.app.common.response.approve.OrderApproveFailed;
import com.food.ordering.app.common.response.approve.OrderApproveSucceeded;
import com.food.ordering.app.common.response.payment.ProcessPaymentFailed;
import com.food.ordering.app.common.response.payment.ProcessPaymentSucceeded;
import com.food.ordering.app.order.service.saga.data.CreateOrderSagaData;
import com.food.ordering.app.order.service.saga.proxy.PaymentServiceProxy;
import com.food.ordering.app.order.service.saga.proxy.RestaurantServiceProxy;
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
  private final RestaurantServiceProxy restaurantServiceProxy;

  private final SagaDefinition<CreateOrderSagaData> sagaDefinition =
      step()
          .invokeLocal(this::createOrder)
          .withCompensation(this::cancelOrder)
          .step()
          .invokeParticipant(this::processPayment)
          .onReply(ProcessPaymentSucceeded.class, this::handleProcessPaymentSucceeded)
          .onReply(ProcessPaymentFailed.class, this::handleProcessPaymentFailed)
          // TODO: handle failure
          .step()
          .invokeParticipant(this::approveOrderByRestaurant)
          .onReply(OrderApproveSucceeded.class, this::handleOrderApproveSucceeded)
          .onReply(OrderApproveFailed.class, this::handleOrderApproveFailed)
          // TODO: handle failure
          .step()
          .invokeLocal(this::confirmOrder)
          .build();


  @Override
  public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }

  private void createOrder(CreateOrderSagaData sagaData) {
    log.info("Create order saga started for order id: {}", sagaData.getOrderId().toString());
//    orderService.createOrder(sagaData.getOrder());
  }

  private void cancelOrder(CreateOrderSagaData data) {
//    orderService.cancelOrder(sagaData.getOrder());
    log.info("CreateOrderSaga cancelled for order id: {}", data.getOrderId().toString());
  }


  private void confirmOrder(CreateOrderSagaData data) {

    log.info("Order: {} was confirmed and approved.", data.getOrderId().toString());
  }

  private void handleOrderApproveFailed(CreateOrderSagaData data,
      OrderApproveFailed orderApproveFailed) {
    log.info("Approve order by restaurant {} failed for order id: {}",
        data.getRestaurantId().toString(), data.getOrderId().toString());
  }

  private void handleOrderApproveSucceeded(CreateOrderSagaData data,
      OrderApproveSucceeded orderApproveSucceeded) {
    log.info("Approve order by restaurant {} succeeded for order id: {}",
        data.getRestaurantId().toString(), data.getOrderId().toString());
  }


  private void handleProcessPaymentSucceeded(CreateOrderSagaData data,
      ProcessPaymentSucceeded response) {
    log.info("Process payment succeeded with for order id: {}, with payment id: {}",
        data.getOrderId().toString(), response.paymentId().toString());
  }

  private void handleProcessPaymentFailed(CreateOrderSagaData data,
      ProcessPaymentFailed processPaymentFailed) {
    log.info("Process payment failed for order id: {}, with payment id: {}",
        data.getOrderId().toString(), processPaymentFailed.paymentId().toString());
  }

  private CommandWithDestination processPayment(CreateOrderSagaData data) {
    log.info("Process payment started for order id: {}", data.getOrderId().toString());
    return paymentServiceProxy.processPayment(data.getOrderId(), data.getCustomerId(),
        data.getPrice());
  }

  private CommandWithDestination approveOrderByRestaurant(CreateOrderSagaData data) {
    log.info("Approve order by restaurant with id {} started for order id: {}",
        data.getRestaurantId().toString(), data.getOrderId().toString());
    return restaurantServiceProxy.approveOrder(data.getOrderId(), data.getCustomerId(),
        data.getRestaurantId());
  }


}
