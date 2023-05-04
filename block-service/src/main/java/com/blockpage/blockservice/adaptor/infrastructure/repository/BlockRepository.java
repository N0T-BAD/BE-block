package com.blockpage.blockservice.adaptor.infrastructure.repository;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

}
