package com.food.ordering.app.restaurant.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true, nullable = false)
  private String name;

  @CurrentTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime lastModifiedAt;

  @Column(nullable = false)
  private Boolean isAvailable;

  @Column(nullable = false)
  private Boolean isDeleted;

//  @Embedded
//  private Address address;

//  private String phoneNumber;

  // TODO:
  // image

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @Fetch(FetchMode.SUBSELECT)
  @Builder.Default
  private List<MenuItem> menuItems = new ArrayList<>();

  @Version
  private Long version;

  public String getStringId() {
    return id.toString();
  }
}
