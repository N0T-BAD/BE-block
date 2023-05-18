package com.blockpage.blockservice.adaptor.infrastructure.mysql.repository;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByOrderId(String orderId);

    List<PaymentEntity> findByMemberIdAndBlockGainTypeIsNotNull(Long memberId);

    List<PaymentEntity> findByMemberIdAndBlockLossTypeIsNotNull(Long memberId);
}
