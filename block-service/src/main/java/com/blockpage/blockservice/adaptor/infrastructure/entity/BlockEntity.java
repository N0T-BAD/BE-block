package com.blockpage.blockservice.adaptor.infrastructure.entity;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockGainType;
import com.blockpage.blockservice.domain.Block;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static BlockEntity toEntity(Block block) {
        return BlockEntity.builder()
            .memberId(block.getMemberId())
            .blockQuantity(block.getBlockQuantity())
            .blockGainType(block.getBlockGainType())
            .blockValidate(block.getBlockValidate())
            .expiredDate(block.getExpiredDate())
            .build();
    }
}