package com.blockpage.blockservice.adaptor.infrastructure.mysql.persistence;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.repository.BlockRepository;
import com.blockpage.blockservice.application.port.out.BlockPort;
import com.blockpage.blockservice.domain.Block;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockAdaptor implements BlockPort {

    private final BlockRepository blockRepository;

    @Override
    public List<BlockEntityDto> getMemberBlock(Long memberId) {
        List<BlockEntity> blockEntities = blockRepository.findByMemberId(memberId);
        return blockEntities.stream()
            .map(BlockEntityDto::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public BlockEntityDto saveBlock(Block block) {
        BlockEntity blockEntity = blockRepository.save(BlockEntity.toEntity(block));
        return BlockEntityDto.toDto(blockEntity);
    }

    @Override
    public List<BlockEntity> updateBlockQuantity(Long memberId) {
        return blockRepository.findByMemberId(memberId);
    }
}
