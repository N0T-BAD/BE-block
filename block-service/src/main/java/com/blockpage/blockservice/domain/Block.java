package com.blockpage.blockservice.domain;

import com.blockpage.blockservice.adaptor.infrastructure.value.BlockGainType;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Block {

    private static final long EXPIRED_BLOCK_YEAR = 5l;

    private Long memberId;
    private Integer blockQuantity;
    private BlockGainType blockGainType;
    private Boolean blockValidate;
    private LocalDateTime expiredDate;


    public static Block initBlock(ChargeBlockQuery query) {
        return Block.builder()
            .memberId(query.getMemberId())
            .blockGainType(BlockGainType.findTypeByValue(query.getType()))
            .blockQuantity(query.getBlockQuantity())
            .blockValidate(Boolean.TRUE)
            .expiredDate(LocalDateTime.now().plusYears(EXPIRED_BLOCK_YEAR))
            .build();
    }
}
