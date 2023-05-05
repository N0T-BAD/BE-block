package com.blockpage.blockservice.adaptor.web.view;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberBlockView {

    private Long memberId;
    private Integer totalBlocks;

    public static MemberBlockView toViewFromMemberBlocks(Long memberId, Integer totalBlocks) {
        return MemberBlockView.builder()
            .memberId(memberId)
            .totalBlocks(totalBlocks)
            .build();
    }
}
