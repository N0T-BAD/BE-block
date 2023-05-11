package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import lombok.Getter;

@Getter
public class BlockView {

    private Long memberId;
    private Integer totalBlocks;

    public BlockView(BlockQueryDto blockQueryDto) {
        memberId = blockQueryDto.getMemberId();
        totalBlocks = blockQueryDto.getTotalBlocks();
    }
}
