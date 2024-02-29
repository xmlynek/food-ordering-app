package com.food.ordering.app.restaurant.service.dto;

import com.food.ordering.app.common.enums.RestaurantOrderTicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RestaurantOrderTicketResponse(UUID orderId,
                                            LocalDateTime createdAt,
                                            RestaurantOrderTicketStatus status,
                                            BigDecimal totalPrice,
                                            List<OrderTicketItemResponse> orderItems) {

}
