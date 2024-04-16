package com.food.ordering.app.restaurant.service.dto;

import java.util.List;
import java.util.UUID;

public record RestaurantResponse(UUID id,
                                 String name,
                                 String description,
                                 AddressDto address,
                                 Boolean isAvailable,
                                 List<MenuItemResponse> menuItems) {

}
