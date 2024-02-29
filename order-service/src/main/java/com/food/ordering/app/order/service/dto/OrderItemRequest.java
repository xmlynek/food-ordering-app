package com.food.ordering.app.order.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequest(@NotNull(message = "ProductId is required") UUID productId,
                               @NotNull(message = "Product quantity is required") @Min(value = 1, message = "Product quantity has to be at least 1") Integer quantity,
                               @NotNull(message = "Product price is required") @DecimalMin(value = "0.00", message = "Item price has to be greater than 0.00") BigDecimal price) {

}
