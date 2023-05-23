package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    EPISODE_BM_PAID(0, "episodeBMPaid"),
    PROFILE_SKIN(1, "profileSkin"),
    NFT(2, "nft"),
    REFUND(3, "refund"),
    NONE(4, "none");

    private int key;
    private String value;

    public static BlockLossType findByValue(String value) {
        return Arrays.stream(BlockLossType.values())
            .filter(t -> t.getValue().equals(value))
            .findFirst()
            .get();
    }
}
