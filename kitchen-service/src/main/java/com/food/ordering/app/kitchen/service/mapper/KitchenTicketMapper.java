package com.food.ordering.app.kitchen.service.mapper;

import com.food.ordering.app.kitchen.service.dto.BasicKitchenTicketResponse;
import com.food.ordering.app.kitchen.service.entity.KitchenTicket;
import com.food.ordering.app.kitchen.service.dto.KitchenTicketDetails;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketDetailsView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {KitchenTicketItemMapper.class})
public interface KitchenTicketMapper {
  BasicKitchenTicketResponse kitchenTicketEntityToBasicKitchenTicketResponse(
      KitchenTicket kitchenTicket);

  @Mapping(target = "ticketItems", source = "ticketItems")
  KitchenTicketDetails kitchenTicketDetailsViewToKitchenTicketDetails(
      KitchenTicketDetailsView kitchenTicketDetailsView);
}
