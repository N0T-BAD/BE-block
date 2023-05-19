package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class BlockView {

    private String message;
    private Long memberId;
    private Integer totalBlocks;

    public BlockView(BlockQueryDto blockQueryDto) {
        memberId = blockQueryDto.getMemberId();
        totalBlocks = blockQueryDto.getTotalBlocks();
    }

    public BlockView(String message) {
        this.message = message;
    }
}
