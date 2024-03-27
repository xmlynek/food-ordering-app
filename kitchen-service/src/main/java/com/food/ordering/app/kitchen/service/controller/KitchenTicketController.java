package com.food.ordering.app.kitchen.service.controller;

import com.food.ordering.app.kitchen.service.dto.BasicKitchenTicketResponse;
import com.food.ordering.app.kitchen.service.dto.KitchenTicketDetails;
import com.food.ordering.app.kitchen.service.mapper.KitchenTicketMapper;
import com.food.ordering.app.kitchen.service.service.KitchenTicketService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kitchen/restaurants/{restaurantId}/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KitchenTicketController {

  private final KitchenTicketService kitchenTicketService;
  private final KitchenTicketMapper kitchenTicketMapper;

  @GetMapping
  public ResponseEntity<List<BasicKitchenTicketResponse>> getRestaurantKitchenTickets(
      @PathVariable(name = "restaurantId") UUID restaurantId) {
    List<BasicKitchenTicketResponse> kitchenTicketResponses = kitchenTicketService.getAllKitchenTicketsByRestaurantId(
            restaurantId).stream()
        .map(kitchenTicketMapper::kitchenTicketEntityToBasicKitchenTicketResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(kitchenTicketResponses);
  }

  @GetMapping("/{ticketId}")
  public ResponseEntity<KitchenTicketDetails> getRestaurantKitchenTicketByKitchenId(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @PathVariable(name = "ticketId") UUID kitchenTicketId) {
    KitchenTicketDetails restaurantOrderTicketResponse = kitchenTicketMapper.kitchenTicketDetailsViewToKitchenTicketDetails(
        kitchenTicketService.getKitchenTicketDetails(restaurantId, kitchenTicketId));
    return ResponseEntity.ok(restaurantOrderTicketResponse);
  }

  @PostMapping("/{ticketId}/complete")
  public ResponseEntity<Void> completeKitchenTicket(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @PathVariable(name = "ticketId") UUID kitchenTicketId) {
    kitchenTicketService.completeKitchenTicket(restaurantId, kitchenTicketId);
    return ResponseEntity.noContent().build();
  }
}
