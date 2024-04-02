package com.food.ordering.app.order.service.entity;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import com.food.ordering.app.common.model.Address;
import io.eventuate.examples.common.money.Money;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID kitchenTicketId;

  private UUID deliveryId;

  @Column(nullable = false, updatable = false)
  private UUID customerId;

  @Column(nullable = false, updatable = false)
  private UUID restaurantId;

  @CurrentTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime lastModifiedAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus orderStatus;

  @Enumerated(EnumType.STRING)
  private KitchenTicketStatus kitchenTicketStatus;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Transient
  private String paymentToken;

  @Embedded
  @Column(nullable = false, updatable = false)
  private Money totalPrice;

  @Embedded
  @Column(nullable = false, updatable = false)
  private Address address;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
  @Fetch(FetchMode.SUBSELECT)
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();

  @ElementCollection
  @Fetch(FetchMode.SUBSELECT)
  @Builder.Default
  private List<String> failureMessages = new ArrayList<>();

  @Version
  private Long version;
}
