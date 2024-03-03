package com.food.ordering.app.payment.service.entity;

import com.food.ordering.app.common.enums.PaymentStatus;
import io.eventuate.examples.common.money.Money;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import org.hibernate.annotations.CurrentTimestamp;

@Entity
@Table(name = "payments")
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

  private String chargeId;

  @Embedded
  private Money amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Version
  private Long version;
}
