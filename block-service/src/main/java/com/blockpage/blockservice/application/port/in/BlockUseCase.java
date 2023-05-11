package com.blockpage.blockservice.application.port.in;

import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BlockUseCase {

    BlockQueryDto findAllBlock(FindBlockQuery findBlockQuery);

    void createBlock(ChargeBlockQuery query);

    void updateBlock(UpdateBlockQuery query);

    @Getter
    @AllArgsConstructor
    class ChargeBlockQuery {

        private Long memberId;
        private String type;
        private Integer blockQuantity;
    }

    @Getter
    @AllArgsConstructor
    class UpdateBlockQuery {

        private Long memberId;
        private Integer blockQuantity;
    }

    @Getter
    @AllArgsConstructor
    class FindBlockQuery {

        private Long memberId;
    }

}
