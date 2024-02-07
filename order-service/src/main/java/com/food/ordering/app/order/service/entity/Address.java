package com.food.ordering.app.order.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String street;
  private String postalCode;
  private String city;

  @OneToOne(mappedBy = "address")
  private Order order;

}
