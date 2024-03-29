package com.food.ordering.app.order.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  @Builder.Default
  private List<MenuItem> menuItems = new ArrayList<>();

  @Column(nullable = false)
  private Boolean isAvailable;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isDeleted = false;

  @Version
  private Long version;

}
