package com.food.ordering.app.order.service.mapper;

import com.food.ordering.app.order.service.dto.AddressRequest;
import com.food.ordering.app.order.service.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  Address addressRequestToAddress(AddressRequest addressRequest);

}
