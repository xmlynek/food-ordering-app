package com.food.ordering.app.common.command;

import com.food.ordering.app.common.model.OrderProduct;
import io.eventuate.tram.commands.common.Command;
import java.util.List;
import java.util.UUID;

public record ApproveOrderCommand(UUID orderId, UUID customerId, UUID restaurantId,
                                  List<OrderProduct> products) implements Command {

}
