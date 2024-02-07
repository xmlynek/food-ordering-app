package com.food.ordering.app.order.mapper;

import com.food.ordering.app.order.dto.OrderCreatedResponse;
import com.food.ordering.app.order.dto.OrderRequest;
import com.food.ordering.app.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, OrderItemMapper.class })
public interface OrderMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "orderStatus", ignore = true)
  @Mapping(target = "failureMessages", ignore = true)
  Order orderRequestToOrderEntity(OrderRequest orderRequest);

  OrderCreatedResponse orderEntityToOrderCreatedResponse(Order order);
}
