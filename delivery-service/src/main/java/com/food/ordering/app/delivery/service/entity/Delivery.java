package com.food.ordering.app.delivery.service.entity;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.model.Address;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "deliveries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private UUID orderId;

  private UUID kitchenTicketId;

  private UUID courierId;

  private UUID customerId;

  @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  @JoinColumn(name = "restaurant_id", updatable = false, nullable = false)
  private Restaurant restaurant;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
      @AttributeOverride(name = "postalCode", column = @Column(name = "delivery_postal_code")),
      @AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
      @AttributeOverride(name = "country", column = @Column(name = "delivery_country"))})
  @Column(nullable = false)
  private Address deliveryAddress;

  @CurrentTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime lastModifiedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private KitchenTicketStatus kitchenTicketStatus;

  @Version
  private Long version;

  public String getStringId() {
    return id.toString();
  }
}
