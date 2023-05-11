package com.blockpage.blockservice.adaptor.infrastructure.entity;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockLossType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
