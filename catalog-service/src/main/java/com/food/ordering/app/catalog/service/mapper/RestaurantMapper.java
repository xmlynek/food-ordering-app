package com.food.ordering.app.catalog.service.mapper;

import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

  @Mappings({
      @Mapping(target = "menuItems", ignore = true),
  })
  Restaurant restaurantCreatedEventToRestaurant(String id,
      RestaurantCreatedEvent restaurantCreatedEvent);

}
