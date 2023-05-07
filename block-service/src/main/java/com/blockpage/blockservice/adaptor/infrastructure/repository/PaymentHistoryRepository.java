package com.blockpage.blockservice.adaptor.infrastructure.repository;

import com.blockpage.blockservice.adaptor.infrastructure.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {

}
