package com.blockpage.blockservice.domain;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort.BlockEntityDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Block {

    private static final long EXPIRED_BLOCK_YEAR = 5l;

    private Long blockId;
    private Long memberId;
    private Integer blockQuantity;
    private GainType blockGainType;
    private Boolean blockValidate;
    private LocalDateTime expiredDate;

    public static List<Block> comsumeBlockList(List<Block> blocks, Integer blockQuantity) {
        if (getTotalBlock(blocks) < blockQuantity) {
            throw new RuntimeException();
        }
        List<Block> sortedBlocks = blocks.stream()
            .filter(Block::isValid)
            .sorted(Comparator.comparing(b -> b.getBlockGainType().getKey()))
            .collect(Collectors.toList());

        List<Block> consumeBlock = new ArrayList<>();
        for (Block block : sortedBlocks) {
            blockQuantity -= block.getBlockQuantity();
            if (blockQuantity <= 0) {
                block.minusQuantity(block.getBlockQuantity() + blockQuantity);
                consumeBlock.add(block);
                break;
            }
            block.minusQuantity(block.getBlockQuantity());
            consumeBlock.add(block);
        }
        return consumeBlock;
    }

    public static Integer getTotalBlock(List<Block> blocks) {
        return blocks.stream()
            .filter(Block::isValid)
            .map(Block::getBlockQuantity)
            .reduce(Integer::sum)
            .get();
    }

    private void minusQuantity(Integer blockQuantity) {
        this.blockQuantity -= blockQuantity;
    }

    public boolean isValid() {
        return this.expiredDate.isAfter(LocalDateTime.now()) && this.blockValidate;
    }


    public static Block initBlockForCharge(ChargeBlockQuery query) {
        return Block.builder()
            .blockId(null)
            .memberId(query.getMemberId())
            .blockGainType(GainType.findByValue(query.getType()))
            .blockQuantity(query.getBlockQuantity())
            .blockValidate(Boolean.TRUE)
            .expiredDate(LocalDateTime.now().plusYears(EXPIRED_BLOCK_YEAR))
            .build();
    }

    public static Block initBlockFromEntityDto(BlockEntityDto dto) {
        return Block.builder()
            .blockId(dto.getId())
            .memberId(dto.getMemberId())
            .blockGainType(GainType.findByValue(dto.getBlockGainType().getValue()))
            .blockQuantity(dto.getBlockQuantity())
            .blockValidate(dto.getBlockValidate())
            .expiredDate(dto.getExpiredDate())
            .build();
    }

    public static Block initBlockFromEntity(BlockEntity blockEntity) {
        return Block.builder()
            .blockId(blockEntity.getId())
            .memberId(blockEntity.getMemberId())
            .blockGainType(GainType.findByValue(blockEntity.getBlockGainType().getValue()))
            .blockQuantity(blockEntity.getBlockQuantity())
            .blockValidate(blockEntity.getBlockValidate())
            .expiredDate(blockEntity.getExpiredDate())
            .build();
    }

    public enum GainType {
        GAME(0, "game"),
        ATTENDANCE(1, "attendance"),
        CASH(2, "cash"),
        ;
        private int key;
        private String value;

        GainType(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getKey() {
            return key;
        }

        public static GainType findByValue(String value) {
            return Arrays.stream(GainType.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .get();
        }
    }

}
