package com.food.ordering.app.restaurant.service.controller;

import com.food.ordering.app.restaurant.service.dto.RestaurantOrderTicketResponse;
import com.food.ordering.app.restaurant.service.mapper.RestaurantOrderTicketMapper;
import com.food.ordering.app.restaurant.service.service.RestaurantOrderTicketService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/orderTickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantOrderController {

  private final RestaurantOrderTicketService restaurantOrderTicketService;
  private final RestaurantOrderTicketMapper restaurantOrderTicketMapper;

  @GetMapping
  public ResponseEntity<List<RestaurantOrderTicketResponse>> getRestaurantOrderTickets(
      @PathVariable(name = "restaurantId") UUID restaurantId) {
    List<RestaurantOrderTicketResponse> menuItems = restaurantOrderTicketService.getAllOrderTicketsByRestaurantId(
            restaurantId).stream()
        .map(restaurantOrderTicketMapper::orderTicketToRestaurantOrderTicketResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(menuItems);
  }

  @GetMapping("/{orderTicketId}")
  public ResponseEntity<RestaurantOrderTicketResponse> getRestaurantOrderTicketByOrderId(
      @PathVariable(name = "restaurantId") UUID restaurantId,
      @PathVariable(name = "orderTicketId") UUID orderTicketId) {
    RestaurantOrderTicketResponse restaurantOrderTicketResponse = restaurantOrderTicketMapper.orderTicketToRestaurantOrderTicketResponse(
        restaurantOrderTicketService.getOrderTicketByRestaurantIdAndOrderId(
            restaurantId, orderTicketId));
    return ResponseEntity.ok(restaurantOrderTicketResponse);
  }
}
