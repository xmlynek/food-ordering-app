package com.food.ordering.app.delivery.service.mapper;

import com.food.ordering.app.common.model.Address;
import com.food.ordering.app.delivery.service.dto.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  AddressResponse addressToAddressResponse(Address address);

  Address addressToAddress(Address address);
}
