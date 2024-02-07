package com.food.ordering.app.payment.service.repository;

import com.food.ordering.app.payment.service.entity.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
