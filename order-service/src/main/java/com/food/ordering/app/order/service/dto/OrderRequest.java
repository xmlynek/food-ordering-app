package com.food.ordering.app.order.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequest(@NotNull(message = "CustomerId is required") UUID customerId,
                           @NotNull(message = "RestaurantId is required") UUID restaurantId,
                           @NotBlank(message = "PaymentToken is required") String paymentToken,
                           @NotNull(message = "Total price is required") @DecimalMin(value = "0.00", message = "Price has to be greater than or equal to 0.00") BigDecimal totalPrice,
                           @NotNull(message = "Address is required") @Valid AddressRequest address,
                           @NotEmpty(message = "List of items cannot be empty") List<@Valid OrderItemRequest> items) {
}
