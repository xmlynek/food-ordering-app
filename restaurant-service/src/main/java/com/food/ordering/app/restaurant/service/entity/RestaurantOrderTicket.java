package com.food.ordering.app.restaurant.service.entity;

import com.food.ordering.app.common.enums.RestaurantOrderTicketStatus;
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

@Entity
@Table(name = "order_tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RestaurantOrderTicket {

  @Id
  private UUID id;

  @Column(nullable = false, updatable = false)
  private UUID customerId;   // TODO: vymazat?

  @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  @JoinColumn(name = "restaurant_id", updatable = false, nullable = false)
  private Restaurant restaurant;

  @OneToMany(mappedBy = "orderTicket", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @Fetch(FetchMode.SUBSELECT)
  private List<OrderTicketItem> orderItems;

  @CurrentTimestamp
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RestaurantOrderTicketStatus status;

  @Column(nullable = false, updatable = false)
  private BigDecimal totalPrice;
}
