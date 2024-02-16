package com.food.ordering.app.order.service.controller;

import com.food.ordering.app.order.service.command.CreateOrderCommand;
import com.food.ordering.app.order.service.dto.OrderCreatedResponse;
import com.food.ordering.app.order.service.dto.OrderRequest;
import com.food.ordering.app.order.service.mapper.OrderMapper;
import com.food.ordering.app.order.service.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
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
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper orderMapper;

  private final CommandGateway commandGateway;

  @GetMapping
  public String getOrderById() {
    return "hello";
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompletableFuture<Object> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
//    Order createdOrder = orderService.createOrder(
//        orderMapper.orderRequestToOrderEntity(orderRequest));

    CreateOrderCommand createOrderCommand = orderMapper.orderRequestToCommand(orderRequest);

    return commandGateway.send(createOrderCommand);


//    return orderMapper.orderEntityToOrderCreatedResponse(createdOrder);
  }

  @GetMapping("/customer/{customerId}")
  public List<OrderCreatedResponse> findAllOrdersByCustomerId(
      @PathVariable("customerId") UUID customerId) {
    return orderService.findAllByCustomerId(customerId).stream()
        .map(orderMapper::orderEntityToOrderCreatedResponse).collect(
            Collectors.toList());
  }

  @GetMapping("/{orderId}")
  public OrderCreatedResponse findOrderByOrderIdAndCustomerId(
      @PathVariable("orderId") UUID orderId) {
    return orderMapper.orderEntityToOrderCreatedResponse(
        orderService.findByOrderId(orderId));
  }

}
