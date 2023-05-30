package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockLossType {
    EPISODE_BM_PAID(0, "episodeBMPaid", "에피소드"),
    PROFILE_SKIN(1, "profileSkin", "프로필 스킨"),
    NFT(2, "nft", "NFT"),
    REFUND(3, "refund", "블럭 환불"),
    NONE(4, "none", "-");

    private int key;
    private String value;
    private String view;

    public static BlockLossType findByValue(String value) {
        return Arrays.stream(BlockLossType.values())
            .filter(t -> t.getValue().equals(value))
            .findFirst()
            .get();
    }
}
