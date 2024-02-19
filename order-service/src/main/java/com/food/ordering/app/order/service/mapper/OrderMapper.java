package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.dto.OrderCreatedResponse;
import com.food.ordering.app.order.service.dto.OrderRequest;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.saga.data.CreateOrderSagaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, OrderItemMapper.class })
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "failureMessages", ignore = true)
  @Mapping(target = "price", expression = "java(new io.eventuate.examples.common.money.Money(orderRequest.getPrice()))")
  Order orderRequestToOrderEntity(OrderRequest orderRequest);

  OrderCreatedResponse orderEntityToOrderCreatedResponse(Order order);

  @Mapping(target = "orderId", source = "id")
  CreateOrderSagaData orderEntityToCreateOrderSagaData(Order order);
}
