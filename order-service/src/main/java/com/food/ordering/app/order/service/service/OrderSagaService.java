package com.food.ordering.app.order.service.service;

import com.food.ordering.app.order.service.entity.Order;

public interface OrderSagaService {

  Order saveOrderAndCreateOrderSaga(Order order);
}
