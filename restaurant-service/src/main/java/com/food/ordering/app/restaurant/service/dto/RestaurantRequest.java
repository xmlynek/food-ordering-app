package com.food.ordering.app.restaurant.service.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(
    @NotBlank(message = "Restaurant name can not be blank") String name
//    @NotBlank(message = "Phone can not be blank") String phone,
    ) {

}
