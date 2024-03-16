package com.food.ordering.app.catalog.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FullRestaurantDto(String id,
                               String name,
                               LocalDateTime createdAt,
                               LocalDateTime lastModifiedAt,
                               Boolean isAvailable,
                               List<MenuItemDto> menuItems) {

}
