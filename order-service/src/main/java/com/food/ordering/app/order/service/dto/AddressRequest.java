package com.food.ordering.app.order.service.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(@NotBlank(message = "Street is required") String street,
                             @NotBlank(message = "Postal code is required") String postalCode,
                             @NotBlank(message = "City is required") String city,
                             @NotBlank(message = "Country is required") String country) {

}
