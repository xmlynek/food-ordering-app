package com.food.ordering.app.common.command;

import com.food.ordering.app.common.model.Address;
import io.eventuate.tram.commands.common.Command;
import java.util.UUID;

public record PrepareOrderDeliveryCommand(UUID orderId, UUID customerId, UUID restaurantId,
                                          Address deliveryAddress) implements Command {

}
