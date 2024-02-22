package com.food.ordering.app.restaurant.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MenuItemRequest(@NotBlank(message = "Menu name can not be blank") String name,
                              @NotBlank(message = "Menu description can not be blank") String description,
                              @NotNull(message = "Menu price is required") @DecimalMin(value = "0.00", message = "Price has to be greater than or equal to 0.00") BigDecimal price) {

}
