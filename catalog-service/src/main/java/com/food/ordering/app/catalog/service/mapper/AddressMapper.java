package com.food.ordering.app.catalog.service.mapper;

import com.food.ordering.app.catalog.service.dto.AddressDto;
import com.food.ordering.app.common.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  AddressDto addressToAddressDto(Address address);

  Address addressToAddress(Address address);
}
