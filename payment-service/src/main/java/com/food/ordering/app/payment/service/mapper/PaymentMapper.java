package com.food.ordering.app.payment.service.mapper;

import com.food.ordering.app.common.command.ProcessPaymentCommand;
import com.food.ordering.app.payment.service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "paymentStatus", ignore = true),
      @Mapping(target = "version", ignore = true),
      @Mapping(target = "chargeId", ignore = true),
      @Mapping(target = "lastModifiedAt", ignore = true),
      @Mapping(target = "refundId", ignore = true)
  })
  Payment paymentRequestToPaymentEntity(ProcessPaymentCommand command);
}
