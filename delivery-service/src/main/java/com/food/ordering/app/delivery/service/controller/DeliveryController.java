package com.food.ordering.app.delivery.service.controller;

import com.food.ordering.app.delivery.service.dto.DeliveryResponse;
import com.food.ordering.app.delivery.service.mapper.DeliveryMapper;
import com.food.ordering.app.delivery.service.service.DeliveryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeliveryController {

  private final DeliveryService deliveryService;
  private final DeliveryMapper deliveryMapper;

  @GetMapping
  public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
    List<DeliveryResponse> deliveryResponses = deliveryService.getAllDeliveryDetailsViews().stream()
        .map(deliveryMapper::deliveryDetailsViewToDeliveryResponse).toList();

    return ResponseEntity.ok(deliveryResponses);
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable UUID deliveryId) {
    DeliveryResponse deliveryResponse = deliveryMapper.deliveryDetailsViewToDeliveryResponse(
        deliveryService.getDeliveryDetailsViewById(deliveryId));

    return ResponseEntity.ok(deliveryResponse);
  }

  @PutMapping("/{deliveryId}")
  public ResponseEntity<DeliveryResponse> updateDeliveryStatusById(@PathVariable UUID deliveryId) {
    DeliveryResponse deliveryResponse = deliveryMapper.deliveryDetailsViewToDeliveryResponse(
        deliveryService.getDeliveryDetailsViewById(deliveryId));

    return ResponseEntity.ok(deliveryResponse);
  }

}
