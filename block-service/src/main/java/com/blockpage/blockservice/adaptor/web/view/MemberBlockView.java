package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.BlockQueryService.BlockQueryDto;
import lombok.Getter;

@Getter
public class MemberBlockView {

    private Long memberId;
    private Integer totalBlocks;

    public MemberBlockView(BlockQueryDto blockQueryDto) {
        memberId = blockQueryDto.getMemberId();
        totalBlocks = blockQueryDto.getTotalBlocks();
    }
}
