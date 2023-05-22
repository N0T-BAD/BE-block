package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    MONEY(0, "현금"),
    CARD(1, "카드"),
    BLOCK(2, "블록"),
    FREE(3,"무료"),
    ;

    private int key;
    private String value;

}
