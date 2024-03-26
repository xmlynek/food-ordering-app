package com.food.ordering.app.kitchen.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record KitchenTicketItemDetails(UUID id,
                                       UUID menuItemId,
                                       Integer quantity,
                                       BigDecimal price,
                                       String name,
                                       String imageUrl) {

}
