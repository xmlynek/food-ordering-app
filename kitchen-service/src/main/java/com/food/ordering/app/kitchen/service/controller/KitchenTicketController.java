package com.food.ordering.app.kitchen.service.controller;

import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.kitchen.service.dto.BasicKitchenTicketResponse;
import com.food.ordering.app.kitchen.service.dto.KitchenTicketDetails;
import com.food.ordering.app.kitchen.service.mapper.KitchenTicketMapper;
import com.food.ordering.app.kitchen.service.service.KitchenTicketService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kitchen/restaurants/{restaurantId}/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KitchenTicketController {

  private final KitchenTicketService kitchenTicketService;
  private final KitchenTicketMapper kitchenTicketMapper;

  @GetMapping
  public ResponseEntity<Page<BasicKitchenTicketResponse>> getRestaurantKitchenTickets(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @RequestParam(name = "ticketStatus", required = false) KitchenTicketStatus ticketStatus,
      @SortDefault(value = "createdAt", direction = Direction.DESC) @PageableDefault Pageable pageable) {
    Page<BasicKitchenTicketResponse> kitchenTicketResponses = kitchenTicketService.getAllKitchenTicketsByRestaurantId(
            restaurantId, pageable, ticketStatus)
        .map(kitchenTicketMapper::kitchenTicketEntityToBasicKitchenTicketResponse);
    return ResponseEntity.ok(kitchenTicketResponses);
  }

  @GetMapping("/{ticketId}")
  @Cacheable(value = "kitchenTicketDetails", key = "{@principalProviderImpl.name, #kitchenTicketId}")
  public KitchenTicketDetails getRestaurantKitchenTicketByKitchenId(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @PathVariable(name = "ticketId") UUID kitchenTicketId) {
    return kitchenTicketMapper.kitchenTicketDetailsViewToKitchenTicketDetails(
        kitchenTicketService.getKitchenTicketDetails(restaurantId, kitchenTicketId));
  }

  @PostMapping("/{ticketId}/complete")
  public ResponseEntity<Void> completeKitchenTicket(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @PathVariable(name = "ticketId") UUID kitchenTicketId) {
    kitchenTicketService.completeKitchenTicket(restaurantId, kitchenTicketId);
    return ResponseEntity.noContent().build();
  }
}
