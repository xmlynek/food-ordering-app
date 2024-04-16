package com.food.ordering.app.common.command;

import io.eventuate.tram.commands.common.Command;
import java.util.UUID;

public record CancelKitchenTicketCommand(UUID ticketId) implements Command {

}
