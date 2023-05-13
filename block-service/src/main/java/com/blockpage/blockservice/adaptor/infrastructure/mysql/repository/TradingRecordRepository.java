package com.blockpage.blockservice.adaptor.infrastructure.mysql.repository;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.TradingRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingRecordRepository extends JpaRepository<TradingRecordEntity, Long> {

}
