package com.food.ordering.app.restaurant.service.mapper;

import com.food.ordering.app.common.model.Address;
import com.food.ordering.app.restaurant.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  Address addressDtoToAddress(AddressDto addressDto);

  AddressDto addressToAddressDto(Address address);

  Address addressToAddress(Address address);
}
