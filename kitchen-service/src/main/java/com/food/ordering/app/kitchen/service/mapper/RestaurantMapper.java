package com.food.ordering.app.kitchen.service.mapper;

import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.kitchen.service.entity.Restaurant;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class, AddressMapper.class})
public interface RestaurantMapper {

  @Mappings({
      @Mapping(target = "menuItems", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "isDeleted", ignore = true),
  })
  Restaurant restaurantCreatedEventToRestaurant(UUID id,
      RestaurantCreatedEvent restaurantCreatedEvent);

//  BasicRestaurantDto restaurantToBasicRestaurantDto(Restaurant restaurant);

//  FullRestaurantDto restaurantToFullRestaurantDto(Restaurant restaurant);
}
