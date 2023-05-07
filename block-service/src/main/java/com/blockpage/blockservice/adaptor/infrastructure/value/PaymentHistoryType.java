package com.blockpage.blockservice.adaptor.infrastructure.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentHistoryType {

    GAIN(0, "충전"),
    LOSS(1, "소비"),
    ;
    private int key;
    private String value;

}
