package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    WEBTOON(0, "webtoon"),
    EPISODE_BM(1 ,"episode"),
    PROFILE_SKIN(2, "profile-skin"),
    NFT(3, "nft"),
    ;

    private int key;
    private String value;

}
