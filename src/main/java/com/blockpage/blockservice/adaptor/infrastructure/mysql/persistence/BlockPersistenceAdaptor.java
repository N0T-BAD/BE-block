package com.blockpage.blockservice.adaptor.infrastructure.mysql.persistence;

import static com.blockpage.blockservice.exception.ErrorCode.*;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.repository.BlockRepository;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.domain.Block;
import com.blockpage.blockservice.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BlockPersistenceAdaptor implements BlockPersistencePort {

    private final BlockRepository blockRepository;

    @Override
    @Transactional
    public void saveBlock(Block block) {
        blockRepository.save(BlockEntity.toEntity(block));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Block> getMemberBlock(String memberId) {
        List<BlockEntity> blockEntities = blockRepository.findByMemberId(memberId);
        return blockEntities.stream()
            .map(Block::toDomainFromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Block getBlockByOrderId(String orderId) {
        BlockEntity entity = blockRepository.findByOrderId(orderId)
            .orElseThrow(() -> new BusinessException(WRONG_ORDER_ID_ERROR.getMessage(), WRONG_ORDER_ID_ERROR.getHttpStatus()));
        return Block.toDomainFromEntity(entity);
    }

    @Override
    @Transactional
    public List<BlockEntity> updateBlockQuantity(String memberId) {
        return blockRepository.findByMemberId(memberId);
    }

    @Override
    @Transactional
    public void deleteBlockByOrderId(String orderId) {
        blockRepository.deleteByOrderId(orderId);
    }
}
