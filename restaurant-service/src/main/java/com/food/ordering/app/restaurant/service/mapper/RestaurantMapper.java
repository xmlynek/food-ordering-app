package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.common.event.RestaurantCreatedEvent;
import com.food.ordering.app.common.event.RestaurantRevisedEvent;
import com.food.ordering.app.restaurant.service.dto.BasicRestaurantResponse;
import com.food.ordering.app.restaurant.service.dto.RestaurantRequest;
import com.food.ordering.app.restaurant.service.dto.RestaurantResponse;
import com.food.ordering.app.restaurant.service.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class, AddressMapper.class})
public interface RestaurantMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "isAvailable", ignore = true),
      @Mapping(target = "lastModifiedAt", ignore = true),
      @Mapping(target = "ownerId", ignore = true),
      @Mapping(target = "isDeleted", ignore = true),
      @Mapping(target = "menuItems", ignore = true)
  })
  Restaurant restaurantRequestToRestaurantEntity(RestaurantRequest restaurantRequest);

  BasicRestaurantResponse restaurantEntityToBasicRestaurantResponse(Restaurant restaurantEntity);

  RestaurantResponse restaurantEntityToRestaurantResponse(Restaurant restaurantEntity);

  RestaurantCreatedEvent restaurantEntityToRestaurantCreateEvent(Restaurant restaurantEntity);

  RestaurantRevisedEvent restaurantEntityToRestaurantRevisedEvent(Restaurant restaurantEntity);
}
