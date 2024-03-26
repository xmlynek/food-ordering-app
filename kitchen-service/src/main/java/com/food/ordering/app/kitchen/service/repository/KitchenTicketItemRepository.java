package com.food.ordering.app.kitchen.service.repository;

import com.food.ordering.app.kitchen.service.entity.KitchenTicketItem;
import com.food.ordering.app.kitchen.service.repository.projection.KitchenTicketItemDetailsView;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenTicketItemRepository extends JpaRepository<KitchenTicketItem, UUID> {

  List<KitchenTicketItemDetailsView> findAllById(UUID id);
}
