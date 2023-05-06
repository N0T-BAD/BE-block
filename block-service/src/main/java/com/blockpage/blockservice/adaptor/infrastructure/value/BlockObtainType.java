package com.blockpage.blockservice.adaptor.infrastructure.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockObtainType {
    CASH(0, "현금"),
    GAME(1, "게임"),
    ATTENDANCE(2, "출석"),
    ;
    private int key;
    private String value;
}
