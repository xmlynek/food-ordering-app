package com.food.ordering.app.delivery.service.controller;

import com.food.ordering.app.delivery.service.dto.DeliveryResponse;
import com.food.ordering.app.delivery.service.mapper.DeliveryMapper;
import com.food.ordering.app.delivery.service.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
  public ResponseEntity<Page<DeliveryResponse>> getAllDeliveries(
      @SortDefault(value = "createdAt", direction = Direction.DESC) @PageableDefault Pageable pageable) {
    Page<DeliveryResponse> deliveryResponses = deliveryService.getAllDeliveryDetailsViews(pageable)
        .map(deliveryMapper::deliveryDetailsViewToDeliveryResponse);

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
