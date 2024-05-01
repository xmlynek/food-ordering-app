package com.food.ordering.app.restaurant.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantUpdateRequest(
    @NotBlank(message = "Restaurant name can not be blank") String name,
    @NotBlank(message = "Restaurant description can not be blank") String description,
    @NotNull(message = "Restaurant availability can not be null") Boolean isAvailable,
    @NotNull(message = "Restaurant address can not be null") @Valid AddressDto address) {

}
