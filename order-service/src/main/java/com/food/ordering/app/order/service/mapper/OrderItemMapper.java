package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.common.model.OrderProduct;
import com.food.ordering.app.order.service.dto.OrderItemRequest;
import com.food.ordering.app.order.service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "order", ignore = true),
      @Mapping(target = "totalPrice", ignore = true)
  })
  OrderItem orderItemRequestToOrderItemEntity(OrderItemRequest orderItemRequest);

  @Mapping(target = "price", expression = "java(new io.eventuate.examples.common.money.Money(orderItem.getPrice()))")
  OrderProduct orderItemEntityToOrderProduct(OrderItem orderItem);
}
