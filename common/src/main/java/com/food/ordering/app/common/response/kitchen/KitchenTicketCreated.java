package com.food.ordering.app.common.response.kitchen;

import java.util.UUID;

public record KitchenTicketCreated(UUID ticketId) implements CreateKitchenTicketResponse {

}
