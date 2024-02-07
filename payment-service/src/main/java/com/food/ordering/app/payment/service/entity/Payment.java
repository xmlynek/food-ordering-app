package com.food.ordering.app.payment.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID customerId;

  private UUID orderId;

  @CurrentTimestamp
  private LocalDateTime createdAt;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

}
