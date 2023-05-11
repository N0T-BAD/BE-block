package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockQuery.FindQuery;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public interface FindBlockPort {

    List<BlockEntityDto> getMemberBlock(FindQuery findQuery);

    @Builder
    @Getter
    class BlockEntityDto {

        private Long id;
        private Long memberId;
        private Integer blockQuantity;
        private Boolean blockValidate;
        private LocalDateTime expiredDate;

        public boolean isExpired() {
            return this.expiredDate.isAfter(LocalDateTime.now());
        }

        public static BlockEntityDto toDto(BlockEntity blockEntity) {
            return BlockEntityDto.builder()
                .id(blockEntity.getId())
                .memberId(blockEntity.getMemberId())
                .blockQuantity(blockEntity.getBlockQuantity())
                .blockValidate(blockEntity.getBlockValidate())
                .expiredDate(blockEntity.getExpiredDate())
                .build();
        }
    }
}
