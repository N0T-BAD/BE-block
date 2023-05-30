package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockGainType {
    GAME(0, "game", "게임"),
    ATTENDANCE(1, "attendance", "출석"),
    CASH(2, "cash", "블럭 충전"),
    NONE(3, "none", "-"),
    ;
    private int key;
    private String value;
    private String view;

    public static BlockGainType findByValue(String value) {
        return Arrays.stream(BlockGainType.values())
            .filter(t -> t.getValue().equals(value))
            .findFirst()
            .get();
    }
}
