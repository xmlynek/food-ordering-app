package com.food.ordering.app.common.command;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.model.Address;
import io.eventuate.tram.commands.common.Command;
import java.util.UUID;

public record CreateDeliveryOrderCommand(UUID orderId, UUID customerId, UUID restaurantId,
                                         UUID kitchenTicketId,
                                         KitchenTicketStatus kitchenTicketStatus,
                                         Address deliveryAddress) implements Command {

}
