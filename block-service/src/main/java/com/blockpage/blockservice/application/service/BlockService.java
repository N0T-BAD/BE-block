package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayRefundResponse;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.domain.Block;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블럭 도메인 이벤트(C,U,D)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService implements BlockUseCase {

    private final BlockPersistencePort blockPersistencePort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<Block> memberBlocks = blockPersistencePort.getMemberBlock(query.getMemberId());
        Integer totalBlocks = Block.getTotalBlock(memberBlocks);
        return new BlockQueryDto(query.getMemberId(), totalBlocks);
    }

    @Override
    @Transactional
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlockForCharge(query);
        blockPersistencePort.saveBlock(block);
    }

    @Override
    @Transactional
    public void consumeBlock(UpdateBlockQuery query) {
        List<BlockEntity> blockEntityList = blockPersistencePort.updateBlockQuantity(query.getMemberId());
        List<Block> blocks = blockEntityList.stream()
            .map(Block::toDomainFromEntity)
            .collect(Collectors.toList());
        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());
        consumeBlockQuantity(blockEntityList, consumeBlocks);
    }

    //==서비스 Layer 편의 메서드==//
    private static void consumeBlockQuantity(List<BlockEntity> blockEntityList, List<Block> consumeBlocks) {
        consumeBlocks.stream().forEach(
            i -> blockEntityList.stream()
                .filter(e -> e.getId() == i.getBlockId())
                .findFirst()
                .get()
                .changeQuantity(i.getBlockQuantity())
        );
    }

    //==서비스 Layer DTO==//
    @Getter
    @AllArgsConstructor
    public class BlockQueryDto {

        private Long memberId;
        private Integer totalBlocks;
    }

    @Getter
    @AllArgsConstructor
    public static class KakaoPayRefundDto {

        private String tid;
        private String status;
        private String orderId;
        private String memberId;
        private Integer amount;
        private Integer approvedCancelAmount;
        private Integer canceledAmount;
        private String itemName;
        private Integer quantity;
        private LocalDateTime createdAt;
        private LocalDateTime approvedAt;
        private LocalDateTime canceledAt;

        public KakaoPayRefundDto(KakaoPayRefundResponse response) {
            this.tid = response.getTid();
            this.status = response.getStatus();
            this.orderId = response.getPartner_order_id();
            this.memberId = response.getPartner_user_id();
            this.amount = response.getAmount().getTotal();
            this.approvedCancelAmount = response.getApproved_cancel_amount().getTotal();
            this.canceledAmount = response.getCanceled_amount().getTotal();
            this.itemName = response.getItem_name();
            this.quantity = response.getQuantity();
            this.createdAt = response.getCreated_at();
            this.approvedAt = response.getApproved_at();
            this.canceledAt = response.getCanceled_at();
        }
    }
}