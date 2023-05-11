package com.blockpage.blockservice.adaptor.infrastructure.value;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockGainType {
    CASH(0, "cash"),
    GAME(1, "game"),
    ATTENDANCE(2, "attendance"),
    ;
    private int key;
    private String value;

    public static BlockGainType findTypeByValue(String value) {
        return Arrays.stream(BlockGainType.values())
            .filter(t -> t.getValue().equals(value))
            .findFirst()
            .get();
    }
}
