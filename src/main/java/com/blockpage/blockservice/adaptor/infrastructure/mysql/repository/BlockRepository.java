package com.blockpage.blockservice.adaptor.infrastructure.mysql.repository;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

    List<BlockEntity> findByMemberId(Long memberId);

    Optional<BlockEntity> findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
}
