package com.blockpage.blockservice.adaptor.infrastructure.mysql.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradingRecordType {

    GAIN(0, "충전"),
    LOSS(1, "소비"),
    ;
    private int key;
    private String value;

}
