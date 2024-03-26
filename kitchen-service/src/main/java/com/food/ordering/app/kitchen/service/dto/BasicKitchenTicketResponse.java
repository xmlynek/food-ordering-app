package com.food.ordering.app.kitchen.service.dto;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BasicKitchenTicketResponse(UUID id,
                                         LocalDateTime createdAt,
                                         KitchenTicketStatus status,
                                         BigDecimal totalPrice) {

}
