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
    private static final long VALID_BLOCK_FOR_REFUND_DAY = 7l;

    private Long blockId;
    private String memberId;
    private String orderId;
    private Integer blockQuantity;
    private GainType gainType;
    private Boolean blockValidate;
    private LocalDateTime expiredDate;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;

    public static List<Block> comsumeBlockList(List<Block> blocks, Integer blockQuantity) {
        if (getTotalBlock(blocks) < blockQuantity) {
            throw new RuntimeException();
        }
        List<Block> sortedBlocks = blocks.stream()
            .filter(Block::isValid)
            .sorted(Comparator.comparing(Block::getExpiredDate))
            .sorted(Comparator.comparing(Block::getGainType, Comparator.comparingInt(GainType::getKey)))
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
            .orElseGet(() -> 0);
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
            .orderId(query.getType().equals(GainType.CASH.getValue()) ? query.getOrderId() : null)
            .memberId(query.getMemberId())
            .gainType(GainType.findByValue(query.getType()))
            .blockQuantity(query.getBlockQuantity())
            .blockValidate(Boolean.TRUE)
            .expiredDate(LocalDateTime.now().plusYears(EXPIRED_BLOCK_YEAR))
            .build();
    }

    public static Block toDomainFromEntity(BlockEntity blockEntity) {
        return Block.builder()
            .blockId(blockEntity.getId())
            .memberId(blockEntity.getMemberId())
            .orderId(blockEntity.getOrderId())
            .gainType(GainType.findByValue(blockEntity.getBlockGainType().getValue()))
            .blockQuantity(blockEntity.getBlockQuantity())
            .blockValidate(blockEntity.getBlockValidate())
            .expiredDate(blockEntity.getExpiredDate())
            .registerTime(blockEntity.getRegisterTime())
            .updateTime(blockEntity.getUpdateTime())
            .build();
    }

    public boolean isAvailableRefund() {
        LocalDateTime availableRefundDate = this.registerTime.plusDays(VALID_BLOCK_FOR_REFUND_DAY);
        return availableRefundDate.isAfter(LocalDateTime.now());
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
