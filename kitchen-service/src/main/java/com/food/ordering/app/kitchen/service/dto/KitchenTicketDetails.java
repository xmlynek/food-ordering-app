package com.food.ordering.app.kitchen.service.dto;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record KitchenTicketDetails(UUID id,
                                   LocalDateTime createdAt,
                                   LocalDateTime lastModifiedAt,
                                   KitchenTicketStatus status,
                                   DeliveryStatus deliveryStatus,
                                   BigDecimal totalPrice,
                                   List<KitchenTicketItemDetails> ticketItems) implements Serializable {

}
