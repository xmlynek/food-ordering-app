package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.dto.OrderCreatedResponse;
import com.food.ordering.app.order.service.dto.OrderDto;
import com.food.ordering.app.order.service.dto.OrderRequest;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.saga.data.CreateOrderSagaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, OrderItemMapper.class})
public interface OrderMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "orderStatus", ignore = true),
      @Mapping(target = "failureMessages", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "kitchenTicketId", ignore = true),
      @Mapping(target = "kitchenTicketStatus", ignore = true),
      @Mapping(target = "totalPrice", expression = "java(new io.eventuate.examples.common.money.Money(orderRequest.totalPrice()))")
  })
  Order orderRequestToOrderEntity(OrderRequest orderRequest);

  OrderCreatedResponse orderEntityToOrderCreatedResponse(Order order);

  @Mappings({
      @Mapping(target = "totalPrice", expression = "java(order.getTotalPrice().getAmount())"),
      @Mapping(target = "failureMessage", expression = "java(String.join(\".\\n\", order.getFailureMessages()))"),
  })
  OrderDto orderEntityToOrderDto(Order order);

  @Mappings({
      @Mapping(target = "orderId", source = "id"),
      @Mapping(target = "paymentId", ignore = true),
      @Mapping(target = "ticketId", ignore = true),
      @Mapping(target = "totalPrice", source = "totalPrice"),
  })
  CreateOrderSagaData orderEntityToCreateOrderSagaData(Order order);
}
