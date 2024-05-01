package com.food.ordering.app.common.event;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import java.util.UUID;

public record KitchenTicketStatusChangedEvent(UUID ticketId, KitchenTicketStatus status) implements KitchenDomainEvent {

}
