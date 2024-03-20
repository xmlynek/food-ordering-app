package com.food.ordering.app.restaurant.service.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(@NotBlank(message = "Street is required") String street,
                         @NotBlank(message = "Postal code is required") String postalCode,
                         @NotBlank(message = "City is required") String city,
                         @NotBlank(message = "Country is required") String country) {

}
