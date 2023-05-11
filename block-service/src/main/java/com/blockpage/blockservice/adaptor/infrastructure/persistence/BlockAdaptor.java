package com.blockpage.blockservice.adaptor.infrastructure.persistence;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.repository.BlockRepository;
import com.blockpage.blockservice.application.port.in.BlockQuery.FindQuery;
import com.blockpage.blockservice.application.port.out.FindBlockPort;
import com.blockpage.blockservice.application.port.out.SaveBlockPort;
import com.blockpage.blockservice.domain.Block;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockAdaptor implements FindBlockPort, SaveBlockPort {

    private final BlockRepository blockRepository;

    @Override
    public List<BlockEntityDto> getMemberBlock(FindQuery findQuery) {
        List<BlockEntity> blockEntities = blockRepository.findByMemberId(findQuery.getMemberId());
        return blockEntities.stream()
            .map(BlockEntityDto::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public BlockEntityDto saveBlock(Block block) {
        BlockEntity blockEntity = blockRepository.save(BlockEntity.toEntity(block));
        return BlockEntityDto.toDto(blockEntity);
    }
}
