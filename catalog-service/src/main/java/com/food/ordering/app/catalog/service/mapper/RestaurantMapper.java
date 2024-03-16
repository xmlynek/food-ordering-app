package com.food.ordering.app.catalog.service.mapper;

import com.food.ordering.app.catalog.service.dto.BasicRestaurantDto;
import com.food.ordering.app.catalog.service.dto.FullRestaurantDto;
import com.food.ordering.app.catalog.service.entity.Restaurant;
import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface RestaurantMapper {

  @Mappings({
      @Mapping(target = "menuItems", ignore = true),
  })
  Restaurant restaurantCreatedEventToRestaurant(String id,
      RestaurantCreatedEvent restaurantCreatedEvent);

  BasicRestaurantDto restaurantToBasicRestaurantDto(Restaurant restaurant);

  FullRestaurantDto restaurantToFullRestaurantDto(Restaurant restaurant);
}
