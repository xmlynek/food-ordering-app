package com.food.ordering.app.restaurant.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderTicketItemResponse(UUID id,
                                      UUID orderTicketId,
                                      UUID menuItemId,
                                      Integer quantity,
                                      BigDecimal price) {

}
