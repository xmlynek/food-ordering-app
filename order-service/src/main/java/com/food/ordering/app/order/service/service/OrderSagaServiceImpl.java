package com.food.ordering.app.order.service.service;

import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.mapper.OrderMapper;
import com.food.ordering.app.order.service.repository.OrderRepository;
import com.food.ordering.app.order.service.saga.CreateOrderSaga;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSagaServiceImpl implements OrderSagaService {

  private final OrderRepository orderRepository;
  private final OrderService orderService;
  private final SagaInstanceFactory sagaInstanceFactory;
  private final CreateOrderSaga createOrderSaga;
  private final OrderMapper orderMapper;


  @Override
  @Transactional
  public Order saveOrderAndCreateOrderSaga(Order order) {
    Order savedOrder = orderService.createOrder(order);
    log.info("Created order {}", savedOrder.getId().toString());
    sagaInstanceFactory.create(createOrderSaga,
        orderMapper.orderEntityToCreateOrderSagaData(savedOrder));
    return savedOrder;
  }

}
