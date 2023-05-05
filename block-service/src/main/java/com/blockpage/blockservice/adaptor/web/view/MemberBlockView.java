package com.blockpage.blockservice.adaptor.web.view;

import lombok.Getter;

@Getter
public class MemberBlockView {

    private Long memberId;
    private Integer totalBlocks;

    public MemberBlockView(Long memberId, Integer totalBlocks) {
        this.memberId = memberId;
        this.totalBlocks = totalBlocks;
    }
}
