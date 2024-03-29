package com.food.ordering.app.kitchen.service.mapper;

import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.kitchen.service.entity.MenuItem;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

  @Mappings({
      @Mapping(target = "id", expression = "java(menuItemId)"),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "isDeleted", ignore = true),
      @Mapping(target = "restaurant", ignore = true),
  })
  MenuItem restaurantMenuItemCreatedEvent(UUID menuItemId, RestaurantMenuItemCreatedEvent event);

}
