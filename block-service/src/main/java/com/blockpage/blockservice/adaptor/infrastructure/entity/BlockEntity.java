package com.blockpage.blockservice.adaptor.infrastructure.entity;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockGainType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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