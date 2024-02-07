package com.food.ordering.app.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequest(@NotNull(message = "CustomerId is required") UUID customerId,
                           @NotNull(message = "RestaurantId is required") UUID restaurantId,
                           @NotNull(message = "Price is required") @DecimalMin(value = "0.00", message = "Price has to be greater than 0.00") BigDecimal price,
                           @NotNull(message = "Address is required") @Valid AddressRequest address,
                           @NotEmpty(message = "List of items cannot be empty") List<@Valid OrderItemRequest> items) {

}
