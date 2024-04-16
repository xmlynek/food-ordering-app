package com.food.ordering.app.catalog.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MenuItemDto(String id,
                          String name,
                          String description,
                          BigDecimal price,
                          Boolean isAvailable,
                          LocalDateTime createdAt,
                          LocalDateTime lastModifiedAt,
                          String imageUrl) {

}
