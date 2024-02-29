package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.restaurant.service.dto.OrderTicketItemResponse;
import com.food.ordering.app.restaurant.service.entity.OrderTicketItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderTicketItemMapper {

  @Mappings({
      @Mapping(target = "orderTicketId", source = "orderTicket.orderId"),
      @Mapping(target = "menuItemId", source = "menuItem.id"),
  })
  OrderTicketItemResponse orderTicketItemToOrderTicketItemResponse(OrderTicketItem orderTicketItem);
}
