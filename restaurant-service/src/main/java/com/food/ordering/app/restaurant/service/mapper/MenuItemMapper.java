package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.common.event.RestaurantMenuItemCreatedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemDeletedEvent;
import com.food.ordering.app.common.event.RestaurantMenuItemRevisedEvent;
import com.food.ordering.app.restaurant.service.dto.MenuItemRequest;
import com.food.ordering.app.restaurant.service.dto.MenuItemResponse;
import com.food.ordering.app.restaurant.service.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "lastModifiedAt", ignore = true),
      @Mapping(target = "isAvailable", ignore = true),
      @Mapping(target = "restaurant", ignore = true),
      @Mapping(target = "isDeleted", ignore = true),
      @Mapping(target = "imageUrl", ignore = true),
  })
  MenuItem menuItemRequestToMenuItem(MenuItemRequest menuItemRequest);

  MenuItemResponse menuItemToMenuItemResponse(MenuItem menuItem);

  @Mapping(target = "restaurantId", source = "restaurant.id")
  RestaurantMenuItemCreatedEvent menuItemToRestaurantMenuItemCreatedEvent(MenuItem menuItem);

  @Mapping(target = "restaurantId", source = "restaurant.id")
  RestaurantMenuItemRevisedEvent menuItemToRestaurantMenuItemRevisedEvent(MenuItem menuItem);

  @Mapping(target = "restaurantId", source = "restaurant.id")
  RestaurantMenuItemDeletedEvent menuItemToRestaurantMenuItemDeletedEvent(MenuItem menuItem);
}
