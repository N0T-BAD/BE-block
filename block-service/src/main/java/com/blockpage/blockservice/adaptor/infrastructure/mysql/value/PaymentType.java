package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    MONEY(0, "현금"),
    CARD(1, "카드"),
    ;
    private int key;
    private String value;

}
