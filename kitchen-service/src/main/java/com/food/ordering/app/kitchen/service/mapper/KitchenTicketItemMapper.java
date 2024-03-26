package com.food.ordering.app.kitchen.service.mapper;

import com.food.ordering.app.kitchen.service.dto.KitchenTicketItemDetails;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketItemDetailsView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface KitchenTicketItemMapper {

  @Mappings({
      @Mapping(target = "menuItemId", source = "menuItem.id"),
      @Mapping(target = "name", source = "menuItem.name"),
      @Mapping(target = "imageUrl", source = "menuItem.imageUrl")
  })
  KitchenTicketItemDetails kitchenTicketItemDetailsViewToKitchenTicketItemDetails(
      KitchenTicketItemDetailsView kitchenTicketItemDetailsView);

}
