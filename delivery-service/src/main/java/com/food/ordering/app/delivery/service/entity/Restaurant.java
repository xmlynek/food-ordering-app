package com.food.ordering.app.delivery.service.entity;

import com.food.ordering.app.common.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Restaurant {

  @Id
  private UUID id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private Boolean isAvailable;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isDeleted = false;

  @Column(nullable = false)
  private LocalDateTime lastModifiedAt;

  @Embedded
  @Column(nullable = false)
  private Address address;

  @Version
  private Long version;

}
