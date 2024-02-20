package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.dto.AddressRequest;
import com.food.ordering.app.order.service.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "order", ignore = true)
  })
  Address addressRequestToAddressEntity(AddressRequest addressRequest);

}
