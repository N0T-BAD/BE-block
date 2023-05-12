package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPort;
import com.blockpage.blockservice.application.port.out.BlockPort.BlockEntityDto;
import com.blockpage.blockservice.domain.Block;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블럭 도메인 이벤트(C,U,D) 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService implements BlockUseCase {

    private final BlockPort blockPort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<BlockEntityDto> blockEntityDtoList = blockPort.getMemberBlock(query.getMemberId());
        List<Block> blocks = blockEntityDtoList.stream()
            .map(Block::initBlockFromEntityDto)
            .collect(Collectors.toList());

        Integer totalBlocks = Block.getTotalBlock(blocks);
        return new BlockQueryDto(query.getMemberId(), totalBlocks);
    }

    @Override
    @Transactional
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlockForCharge(query);
        blockPort.saveBlock(block);
    }

    @Override
    @Transactional
    public void updateBlock(UpdateBlockQuery query) {
        List<BlockEntity> blockEntityList = blockPort.updateBlockQuantity(query.getMemberId());
        List<Block> blocks = blockEntityList.stream()
            .map(Block::initBlockFromEntity)
            .collect(Collectors.toList());

        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());

        consumeBlocks.stream().forEach(
            i -> blockEntityList.stream()
                .filter(e -> e.getId() == i.getBlockId())
                .findFirst()
                .get()
                .changeQuantity(i.getBlockQuantity())
        );
    }

    @Getter
    @AllArgsConstructor
    public class BlockQueryDto {

        private Long memberId;
        private Integer totalBlocks;
    }
}
