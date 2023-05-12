package com.blockpage.blockservice.adaptor.infrastructure.value;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockGainType {
    GAME(0, "game"),
    ATTENDANCE(1, "attendance"),
    CASH(2, "cash"),
    ;
    private int key;
    private String value;

    public static BlockGainType findByValue(String value) {
        return Arrays.stream(BlockGainType.values())
            .filter(t -> t.getValue().equals(value))
            .findFirst()
            .get();
    }
}
