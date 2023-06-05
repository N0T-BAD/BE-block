package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import com.blockpage.blockservice.domain.Block;
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
public class BlockService implements BlockUseCase {

    private final BlockPersistencePort blockPersistencePort;
    private final PaymentPersistencePort paymentPersistencePort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<Block> memberBlocks = blockPersistencePort.getMemberBlock(query.getMemberId());
        Integer totalBlocks = Block.getTotalBlock(memberBlocks);
        return new BlockQueryDto(query.getMemberId(), totalBlocks);
    }

    @Override
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlockForCharge(query);
        switch (BlockGainType.findByValue(query.getType())) {
            case CASH -> {
                blockPersistencePort.saveBlock(block);
            }
            case GAME, ATTENDANCE -> {
                paymentPersistencePort.savePaymentRecord(PaymentEntityDto.initForGameAndAttendance(query));
                blockPersistencePort.saveBlock(block);
            }
        }
    }

    @Override
    public void consumeBlock(UpdateBlockQuery query) {
        List<BlockEntity> blockEntityList = blockPersistencePort.updateBlockQuantity(query.getMemberId());
        List<Block> blocks = blockEntityList.stream()
            .map(Block::toDomainFromEntity)
            .collect(Collectors.toList());
        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());
        consumeBlockQuantity(blockEntityList, consumeBlocks);
        paymentPersistencePort.savePaymentRecord(PaymentEntityDto.initForInternalService(query));
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

        private String memberId;
        private Integer totalBlocks;
    }
}