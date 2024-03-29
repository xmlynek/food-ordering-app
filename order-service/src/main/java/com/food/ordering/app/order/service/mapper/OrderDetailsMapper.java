package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.dto.OrderDetails;
import com.food.ordering.app.order.service.dto.OrderItemDetails;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.entity.OrderItem;
import com.food.ordering.app.order.service.repository.projection.OrderMenuItemDetailsView;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

  @Mappings({
    @Mapping(target = "totalPrice", source = "totalPrice.amount"),
    @Mapping(target = "items", expression = "java(itemDetails)"),
    @Mapping(target = "failureMessage", expression = "java(String.join(\".\\n\", order.getFailureMessages()))"),
  })
  OrderDetails toOrderDetails(Order order, @Context List<OrderItemDetails> itemDetails);

  OrderItemDetails toOrderItemDetails(
      OrderItem orderItem, OrderMenuItemDetailsView view);
}
