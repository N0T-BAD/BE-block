package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    EPISODE_BM(0, "episode"),
    PROFILE_SKIN(1, "profile-skin"),
    NFT(2, "nft"),
    NONE(3, "none");

    private int key;
    private String value;

}
