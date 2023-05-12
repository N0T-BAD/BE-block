package com.blockpage.blockservice.adaptor.infrastructure.repository;

import com.blockpage.blockservice.adaptor.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
