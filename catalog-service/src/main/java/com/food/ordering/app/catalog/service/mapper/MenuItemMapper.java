package com.food.ordering.app.catalog.service.mapper;

import com.food.ordering.app.catalog.service.dto.MenuItemDto;
import com.food.ordering.app.catalog.service.entity.MenuItem;
import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

  @Mappings({
      @Mapping(target = "id", expression = "java(menuItemId)"),
  })
  MenuItem restaurantMenuItemCreatedEvent(String menuItemId, RestaurantMenuItemCreatedEvent event);

  @Mappings({
    @Mapping(target = "createdAt", ignore = true),
    @Mapping(target = "id", expression = "java(menuItemId)"),
  })
  MenuItem restaurantMenuItemRevisedEvent(String menuItemId, RestaurantMenuItemRevisedEvent event);

  MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);
}
