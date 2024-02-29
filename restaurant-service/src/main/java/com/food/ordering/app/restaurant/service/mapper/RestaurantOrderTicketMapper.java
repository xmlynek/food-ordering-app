package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.restaurant.service.dto.RestaurantOrderTicketResponse;
import com.food.ordering.app.restaurant.service.entity.RestaurantOrderTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderTicketItemMapper.class})
public interface RestaurantOrderTicketMapper {

  RestaurantOrderTicketResponse orderTicketToRestaurantOrderTicketResponse(
      RestaurantOrderTicket orderTicket);
}
