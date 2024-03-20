package com.food.ordering.app.catalog.service.dto;

public record BasicRestaurantDto(String id,
                                 String name,
                                 String description,
                                 AddressDto address,
                                 Boolean isAvailable) {

}
