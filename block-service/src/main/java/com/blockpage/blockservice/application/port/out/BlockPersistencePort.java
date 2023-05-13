package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.domain.Block;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public interface BlockPersistencePort {

    List<BlockEntityDto> getMemberBlock(Long memberId);
    BlockEntityDto saveBlock(Block block);
    List<BlockEntity> updateBlockQuantity(Long memberId);

    @Builder
    @Getter
    class BlockEntityDto {

        private Long id;
        private Long memberId;
        private Integer blockQuantity;
        private Boolean blockValidate;
        private LocalDateTime expiredDate;
        private BlockGainType blockGainType;

        public static BlockEntityDto toDto(BlockEntity blockEntity) {
            return BlockEntityDto.builder()
                .id(blockEntity.getId())
                .memberId(blockEntity.getMemberId())
                .blockQuantity(blockEntity.getBlockQuantity())
                .blockGainType(blockEntity.getBlockGainType())
                .blockValidate(blockEntity.getBlockValidate())
                .expiredDate(blockEntity.getExpiredDate())
                .build();
        }
    }
}
