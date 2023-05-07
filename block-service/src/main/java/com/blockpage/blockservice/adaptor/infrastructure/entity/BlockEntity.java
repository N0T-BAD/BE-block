package com.blockpage.blockservice.adaptor.infrastructure.entity;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockGainType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Entity
@Table(name = "block")
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long memberId;
    @Column
    private Integer blockQuantity;
    @Column
    private BlockGainType blockGainType;
    @Column
    private Boolean blockValidate;
    @Column
    private LocalDateTime expiredDate;
}