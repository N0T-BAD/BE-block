package com.blockpage.blockservice.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BlockUseCase {

    void createBlock(ChargeBlockQuery query);

    @Getter
    @AllArgsConstructor
    class ChargeBlockQuery {

        private Long memberId;
        private String type;
        private Integer blockQuantity;
    }
}
