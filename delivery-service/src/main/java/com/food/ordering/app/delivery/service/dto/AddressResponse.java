package com.food.ordering.app.delivery.service.dto;

import java.io.Serializable;

public record AddressResponse(String street,
                              String postalCode,
                              String city,
                              String country) implements Serializable {

}
