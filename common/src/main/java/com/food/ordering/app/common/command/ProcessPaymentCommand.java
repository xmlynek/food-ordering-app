package com.food.ordering.app.common.command;

import io.eventuate.examples.common.money.Money;
import io.eventuate.tram.commands.common.Command;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProcessPaymentCommand(UUID orderId, UUID customerId, Money amount) implements
    Command {

}
