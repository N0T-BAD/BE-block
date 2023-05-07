package com.blockpage.blockservice.adaptor.infrastructure.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    EPISODE_BM(0 ,"에피소드 BM 결제"),
    PROFILE_SKIN(1, "프로필 스킨 결제"),
    NFT(2, "NFT 결제"),
    ;

    private int key;
    private String value;

}
