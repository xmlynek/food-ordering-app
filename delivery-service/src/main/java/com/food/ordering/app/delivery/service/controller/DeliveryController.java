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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<Page<DeliveryResponse>> getAllAvailableDeliveries(
      @SortDefault(value = "createdAt", direction = Direction.DESC) @PageableDefault Pageable pageable) {
    Page<DeliveryResponse> deliveryResponses = deliveryService.getAllAvailableDeliveryDetailsViews(pageable)
        .map(deliveryMapper::deliveryDetailsViewToDeliveryResponse);

    return ResponseEntity.ok(deliveryResponses);
  }

  @GetMapping("/history")
  public ResponseEntity<Page<DeliveryResponse>> getCourierDeliveryHistory(
      @SortDefault(value = "createdAt", direction = Direction.DESC) @PageableDefault Pageable pageable) {
    Page<DeliveryResponse> deliveryResponses = deliveryService.getDeliveryHistoryForCourier(
            UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()), pageable)
        .map(deliveryMapper::deliveryDetailsViewToDeliveryResponse);

    return ResponseEntity.ok(deliveryResponses);
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable UUID deliveryId) {
    DeliveryResponse deliveryResponse = deliveryMapper.deliveryDetailsViewToDeliveryResponse(
        deliveryService.getDeliveryDetailsViewById(deliveryId));

    return ResponseEntity.ok(deliveryResponse);
  }

  @PostMapping("/{deliveryId}/assign")
  public ResponseEntity<Void> assignDeliveryToCourier(@PathVariable UUID deliveryId) {
    deliveryService.assignDeliveryToCourier(deliveryId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{deliveryId}/pick-up")
  public ResponseEntity<Void> pickUpDelivery(@PathVariable UUID deliveryId) {
    deliveryService.pickUpDelivery(deliveryId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{deliveryId}/complete")
  public ResponseEntity<Void> completeDelivery(@PathVariable UUID deliveryId) {
    deliveryService.completeDelivery(deliveryId);

    return ResponseEntity.ok().build();
  }

}
