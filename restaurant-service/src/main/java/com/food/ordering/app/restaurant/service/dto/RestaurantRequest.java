package com.food.ordering.app.restaurant.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RestaurantRequest(@NotBlank(message = "Restaurant name can not be blank") String name,
                                @NotBlank(message = "Restaurant description can not be blank")
                                @Size(max = 255, message = "Restaurant description must not be longer than 255 characters")
                                String description,
                                @NotNull(message = "Restaurant address can not be null") @Valid AddressDto address) {

}
