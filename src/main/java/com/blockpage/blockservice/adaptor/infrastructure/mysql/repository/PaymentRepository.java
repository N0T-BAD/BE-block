package com.blockpage.blockservice.adaptor.infrastructure.mysql.repository;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByOrderId(String orderId);

    List<PaymentEntity> findByMemberIdAndBlockGainTypeNot(Long memberId, BlockGainType blockGainType);

    List<PaymentEntity> findByMemberIdAndBlockLossTypeNot(Long memberId, BlockLossType blockLossType);
}
