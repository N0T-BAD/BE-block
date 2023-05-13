package com.blockpage.blockservice.adaptor.infrastructure.mysql.repository;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByOrderId(String orderId);
}
