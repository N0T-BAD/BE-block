package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.FindBlockPort.BlockEntityDto;
import com.blockpage.blockservice.application.port.out.SaveBlockPort;
import com.blockpage.blockservice.domain.Block;
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

    private final SaveBlockPort saveBlockPort;

    @Override
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlock(query);
        saveBlockPort.saveBlock(block);
    }
}
