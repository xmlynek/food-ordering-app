package com.food.ordering.app.kitchen.service.entity;

import com.food.ordering.app.common.enums.DeliveryStatus;
import com.food.ordering.app.common.enums.KitchenTicketStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "kitchen_tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KitchenTicket {

  @Id
  private UUID id;

  @Column(nullable = false, updatable = false)
  private UUID customerId;

  private UUID deliveryId;

  @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  @JoinColumn(name = "restaurant_id", updatable = false, nullable = false)
  private Restaurant restaurant;

  @OneToMany(mappedBy = "kitchenTicket", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @Fetch(FetchMode.SUBSELECT)
  private List<KitchenTicketItem> ticketItems;

  @CurrentTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime lastModifiedAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private KitchenTicketStatus status;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @Column(nullable = false, updatable = false)
  private BigDecimal totalPrice;

  public String getStringId() {
    return id.toString();
  }
}
