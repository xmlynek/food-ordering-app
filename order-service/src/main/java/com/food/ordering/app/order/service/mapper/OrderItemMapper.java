package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.order.service.dto.OrderItemRequest;
import com.food.ordering.app.order.service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "totalPrice", ignore = true)
  OrderItem orderItemRequestToOrderItemEntity(OrderItemRequest orderItemRequest);

  OrderProduct orderItemEntityToOrderProduct(OrderItem orderItem);
}
