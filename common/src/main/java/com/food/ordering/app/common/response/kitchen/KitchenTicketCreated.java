package com.food.ordering.app.common.response.kitchen;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import java.util.UUID;

public record KitchenTicketCreated(UUID ticketId, KitchenTicketStatus status) implements
    CreateKitchenTicketResponse {

}
