package com.food.ordering.app.restaurant.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponse(UUID id,
                               String name,
                               String description,
                               BigDecimal price) {

}
