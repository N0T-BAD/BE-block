package com.blockpage.blockservice.adaptor.infrastructure.entity;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockLossType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "payment_history")
public class PaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long beneficiaryId;

    @Column
    private Long episodeBMId;

    @Column
    private Integer BlockQuantity;

    @Column
    @Enumerated(EnumType.STRING)
    private BlockLossType blockLossType;
}
