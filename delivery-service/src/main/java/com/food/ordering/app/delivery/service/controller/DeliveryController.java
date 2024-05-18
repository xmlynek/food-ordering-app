package com.food.ordering.app.delivery.service.controller;

import com.food.ordering.app.delivery.service.dto.DeliveryResponse;
import com.food.ordering.app.delivery.service.mapper.DeliveryMapper;
import com.food.ordering.app.delivery.service.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
  @Cacheable(value = "availableDeliveries", key = "{#pageable}")
  public Page<DeliveryResponse> getAllAvailableDeliveries(
      @SortDefault(value = "createdAt", direction = Direction.DESC) @PageableDefault Pageable pageable) {
    return deliveryService.getAllAvailableDeliveryDetailsViews(pageable)
        .map(deliveryMapper::deliveryDetailsViewToDeliveryResponse);
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
  @Cacheable(value = "deliveryDetails", key = "{#deliveryId}")
  public DeliveryResponse getDeliveryById(@PathVariable UUID deliveryId) {
    return deliveryMapper.deliveryDetailsViewToDeliveryResponse(
        deliveryService.getDeliveryDetailsViewById(deliveryId));
  }

  @PostMapping("/{deliveryId}/assign")
  @Caching(evict = {
      @CacheEvict(value = "deliveryDetails", key = "{#deliveryId}", beforeInvocation = true),
      @CacheEvict(value = "availableDeliveries", allEntries = true, beforeInvocation = true)
  })
  public ResponseEntity<Void> assignDeliveryToCourier(@PathVariable UUID deliveryId) {
    deliveryService.assignDeliveryToCourier(deliveryId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{deliveryId}/pick-up")
  @CacheEvict(value = "deliveryDetails", key = "{#deliveryId}", beforeInvocation = true)
  public ResponseEntity<Void> pickUpDelivery(@PathVariable UUID deliveryId) {
    deliveryService.pickUpDelivery(deliveryId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{deliveryId}/complete")
  @CacheEvict(value = "deliveryDetails", key = "{#deliveryId}", beforeInvocation = true)
  public ResponseEntity<Void> completeDelivery(@PathVariable UUID deliveryId) {
    deliveryService.completeDelivery(deliveryId);

    return ResponseEntity.ok().build();
  }

}
