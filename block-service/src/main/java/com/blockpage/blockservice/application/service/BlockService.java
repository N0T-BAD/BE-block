package com.blockpage.blockservice.application.service;

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

/**
 * 블럭 도메인 이벤트(C,U,D) 서비스
 */
@Service
@RequiredArgsConstructor
public class BlockService implements BlockUseCase {

    private final BlockPort blockPort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<BlockEntityDto> blockEntityDtoList = blockPort.getMemberBlock(query.getMemberId());
        List<Block> blocks = blockEntityDtoList.stream()
            .map(Block::initBlockFromEntityDto)
            .collect(Collectors.toList());

        Integer totalBlocks = blocks.stream()
            .filter(Block::isValid)
            .map(Block::getBlockQuantity)
            .reduce(Integer::sum)
            .get();

        return new BlockQueryDto(query.getMemberId(), totalBlocks);
    }

    @Override
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlockForCharge(query);
        blockPort.saveBlock(block);
    }

    @Override
    public void updateBlock(UpdateBlockQuery query) {
        List<BlockEntityDto> blockEntityDtoList = blockPort.getMemberBlock(query.getMemberId());
        List<Block> blocks = blockEntityDtoList.stream()
            .map(Block::initBlockFromEntityDto)
            .collect(Collectors.toList());

        /*
        추가 개발 필요
         */
        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());

    }

    @Getter
    @AllArgsConstructor
    public class BlockQueryDto {

        private Long memberId;
        private Integer totalBlocks;
    }
}
