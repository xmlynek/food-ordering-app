package com.food.ordering.app.order.mapper;

import com.food.ordering.app.order.dto.AddressRequest;
import com.food.ordering.app.order.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  Address addressRequestToAddressEntity(AddressRequest addressRequest);

}
