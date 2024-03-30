package com.food.ordering.app.delivery.service.repository;

import com.food.ordering.app.delivery.service.entity.Delivery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

  @NonNull
  <T> Optional<T> findById(@NonNull UUID id, @NonNull Class<T> type);

  @NonNull
  <T> List<T> findAllBy(@NonNull Class<T> type);

  @NonNull
  Optional<Delivery> findByOrderId(@NonNull UUID orderId);

}
