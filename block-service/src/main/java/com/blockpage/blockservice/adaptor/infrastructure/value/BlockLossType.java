package com.blockpage.blockservice.adaptor.infrastructure.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    WEBTOON(0, "웹툰"),
    EPISODE_BM(1 ,"에피소드"),
    PROFILE_SKIN(2, "프로필 스킨"),
    NFT(3, "NFT"),
    ;

    private int key;
    private String value;

}
