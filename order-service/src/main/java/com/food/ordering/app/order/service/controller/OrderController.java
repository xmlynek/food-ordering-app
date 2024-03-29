package com.food.ordering.app.order.service.controller;

import com.food.ordering.app.order.service.dto.OrderCreatedResponse;
import com.food.ordering.app.order.service.dto.OrderDetails;
import com.food.ordering.app.order.service.dto.OrderDto;
import com.food.ordering.app.order.service.dto.OrderRequest;
import com.food.ordering.app.order.service.entity.Order;
import com.food.ordering.app.order.service.mapper.OrderMapper;
import com.food.ordering.app.order.service.service.OrderSagaService;
import com.food.ordering.app.order.service.service.OrderService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

  private final OrderService orderService;
  private final OrderSagaService orderSagaService;
  private final OrderMapper orderMapper;


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("isFullyAuthenticated() && #orderRequest.customerId().toString() == authentication.name")
  public ResponseEntity<OrderCreatedResponse> createOrder(
      @Valid @RequestBody OrderRequest orderRequest) {
    Order createdOrder = orderSagaService.saveOrderAndCreateOrderSaga(
        orderMapper.orderRequestToOrderEntity(orderRequest));
    OrderCreatedResponse orderCreatedResponse = orderMapper.orderEntityToOrderCreatedResponse(
        createdOrder);
    return new ResponseEntity<>(orderCreatedResponse, HttpStatus.CREATED);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable("orderId") UUID orderId) {
    return ResponseEntity.ok(orderService. getOrderDetailsById(orderId));
  }

  @GetMapping("/customer/{customerId}")
  @PreAuthorize("isFullyAuthenticated() && #customerId == authentication.name")
  public ResponseEntity<Page<OrderDto>> findAllOrdersByCustomerId(
      @PathVariable("customerId") String customerId,
      @SortDefault(value = "createdAt", direction = Direction.DESC) Pageable pageable) {
    Page<OrderDto> orders = orderService.findAllByCustomerId(UUID.fromString(customerId), pageable)
        .map(orderMapper::orderEntityToOrderDto);
    return ResponseEntity.ok(orders);
  }
}
