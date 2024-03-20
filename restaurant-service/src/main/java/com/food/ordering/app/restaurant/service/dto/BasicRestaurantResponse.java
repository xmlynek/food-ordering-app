package com.food.ordering.app.restaurant.service.dto;

import java.util.UUID;

public record BasicRestaurantResponse(UUID id,
                                      String name,
                                      String description,
                                      AddressDto address,
                                      Boolean isAvailable) {

}
