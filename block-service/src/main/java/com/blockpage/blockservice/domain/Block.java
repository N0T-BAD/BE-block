package com.blockpage.blockservice.domain;

import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.port.out.BlockPort.BlockEntityDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Block {

    private static final long EXPIRED_BLOCK_YEAR = 5l;

    private Long memberId;
    private Integer blockQuantity;
    private GainType blockGainType;
    private Boolean blockValidate;
    private LocalDateTime expiredDate;

    public boolean isExpired() {
        return this.expiredDate.isAfter(LocalDateTime.now());
    }


    public static Block initBlockForCharge(ChargeBlockQuery query) {
        return Block.builder()
            .memberId(query.getMemberId())
            .blockGainType(GainType.findByValue(query.getType()))
            .blockQuantity(query.getBlockQuantity())
            .blockValidate(Boolean.TRUE)
            .expiredDate(LocalDateTime.now().plusYears(EXPIRED_BLOCK_YEAR))
            .build();
    }

    public static Block initBlockFromEntityDto(BlockEntityDto dto) {
        return Block.builder()
            .memberId(dto.getMemberId())
            .blockGainType(GainType.findByValue(dto.getBlockGainType().getValue()))
            .blockQuantity(dto.getBlockQuantity())
            .blockValidate(dto.getBlockValidate())
            .expiredDate(dto.getExpiredDate())
            .build();
    }

    enum GainType {
        CASH("cash"), GAME("game"), ATTENDANCE("attendance");
        private String value;

        GainType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static GainType findByValue(String value) {
            return Arrays.stream(GainType.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .get();
        }
    }

}
