package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.command.CreateOrderCommand;
import com.food.ordering.app.order.service.dto.OrderCreatedResponse;
import com.food.ordering.app.order.service.dto.OrderRequest;
import com.food.ordering.app.order.service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, OrderItemMapper.class })
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "failureMessages", ignore = true)
  Order orderRequestToOrderEntity(OrderRequest orderRequest);

  @Mapping(target = "city", source = "address.city")
  @Mapping(target = "postalCode", source = "address.postalCode")
  @Mapping(target = "street", source = "address.street")
  @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID())")
  CreateOrderCommand orderRequestToCommand(OrderRequest orderRequest);


  OrderCreatedResponse orderEntityToOrderCreatedResponse(Order order);
}
