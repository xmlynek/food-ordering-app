package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.dto.RestaurantResponse;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface RestaurantMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "isAvailable", ignore = true),
      @Mapping(target = "isDeleted", ignore = true),
      @Mapping(target = "menuItems", ignore = true)
  })
  Restaurant restaurantRequestToRestaurantEntity(RestaurantRequest restaurantRequest);

  RestaurantResponse restaurantEntityToRestaurantResponse(Restaurant restaurantEntity);
}