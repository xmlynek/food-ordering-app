package com.food.ordering.app.delivery.service.repository;

import com.food.ordering.app.delivery.service.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

  @NonNull
  <T> Optional<T> findById(@NonNull UUID id, @NonNull Class<T> type);

  @NonNull
  <T> Page<T> findAllBy(Pageable pageable, @NonNull Class<T> type);

  @NonNull
  @PreAuthorize("isFullyAuthenticated() and #courierId.toString() == authentication.name")
  Optional<Delivery> findByIdAndCourierId(@NonNull UUID id, @NonNull UUID courierId);

  @NonNull
  Optional<Delivery> findByKitchenTicketId(@NonNull UUID kitchenTicketId);
}
