package com.food.ordering.app.order.service.event.handler;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.event.KitchenTicketStatusChangedEvent;
import com.food.ordering.app.order.service.service.OrderService;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderServiceEventHandler {

  private final OrderService orderService;


  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder.forAggregateType(
            "com.food.ordering.app.kitchen.service.entity.KitchenTicket")
        .onEvent(KitchenTicketStatusChangedEvent.class, this::handleKitchenTicketStatusChangedEvent)
        .build();
  }


  private void handleKitchenTicketStatusChangedEvent(
      DomainEventEnvelope<KitchenTicketStatusChangedEvent> de) {
    try {
      UUID ticketId = UUID.fromString(de.getAggregateId());
      KitchenTicketStatusChangedEvent event = de.getEvent();
      KitchenTicketStatus status = event.status();
      log.info(
          "Handling KitchenTicketStatusChangedEvent with status {} for order with ticket ID {}",
          status, ticketId);

      orderService.updateKitchenTicketStatus(ticketId, status);

      log.info(
          "Successfully handled KitchenTicketStatusChangedEvent for order with ticket ID {} and status {}",
          ticketId, status);
    } catch (Exception e) {
      log.error("Error handling KitchenTicketStatusChangedEvent for order with ticket ID {}: {}",
          de.getAggregateId(), e.getMessage(), e);
    }
  }
}
