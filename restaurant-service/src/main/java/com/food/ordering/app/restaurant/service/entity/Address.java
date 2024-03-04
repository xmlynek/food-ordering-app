package com.food.ordering.app.restaurant.service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Embeddable
public class Address {

  private String street;
  private String postalCode;
  private String city;
}
