package com.food.ordering.app.delivery.service.mapper;

import com.food.ordering.app.common.command.PrepareOrderDeliveryCommand;
import com.food.ordering.app.common.event.DeliveryAssignedToCourierEvent;
import com.food.ordering.app.common.event.DeliveryPickedUpEvent;
import com.food.ordering.app.common.event.DeliveryCompletedEvent;
import com.food.ordering.app.delivery.service.dto.DeliveryResponse;
import com.food.ordering.app.delivery.service.entity.Delivery;
import com.food.ordering.app.delivery.service.repository.projection.DeliveryDetailsView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface DeliveryMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
      @Mapping(target = "lastModifiedAt", expression = "java(java.time.LocalDateTime.now())"),
      @Mapping(target = "deliveryStatus", ignore = true),
      @Mapping(target = "restaurant", ignore = true),
      @Mapping(target = "courierId", ignore = true),
  })
  Delivery prepareOrderDeliveryCommandToDelivery(PrepareOrderDeliveryCommand command);


  @Mappings({
      @Mapping(target = "restaurantId", source = "restaurant.id"),
      @Mapping(target = "restaurantName", source = "restaurant.name"),
      @Mapping(target = "restaurantAddress", source = "restaurant.address"),
  })
  DeliveryResponse deliveryDetailsViewToDeliveryResponse(DeliveryDetailsView view);

  @Mappings({
      @Mapping(target = "status", source = "deliveryStatus"),
      @Mapping(target = "deliveryId", source = "id"),
      @Mapping(target = "assignedAt", expression = "java(java.time.LocalDateTime.now())"),
  })
  DeliveryAssignedToCourierEvent deliveryToDeliveryAssignedToCourierEvent(Delivery delivery);

  @Mappings({
      @Mapping(target = "status", source = "deliveryStatus"),
      @Mapping(target = "deliveryId", source = "id"),
      @Mapping(target = "pickedUpAt", expression = "java(java.time.LocalDateTime.now())"),
  })
  DeliveryPickedUpEvent deliveryToDeliveryPickedUpEvent(Delivery delivery);

  @Mappings({
      @Mapping(target = "status", source = "deliveryStatus"),
      @Mapping(target = "deliveryId", source = "id"),
      @Mapping(target = "completedAt", expression = "java(java.time.LocalDateTime.now())"),
  })
  DeliveryCompletedEvent deliverySuccessfullyDeliveredEvent(Delivery delivery);
}
