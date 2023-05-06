package com.blockpage.blockservice.adaptor.infrastructure.repository;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

    List<BlockEntity> findByMemberId(Long memberId);
}
