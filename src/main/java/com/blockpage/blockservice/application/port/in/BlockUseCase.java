package com.blockpage.blockservice.application.port.in;

import com.blockpage.blockservice.adaptor.web.requestbody.BlockRequest;
import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface BlockUseCase {

    BlockQueryDto findAllBlock(FindBlockQuery findBlockQuery);
    void createBlock(ChargeBlockQuery query);
    void consumeBlock(UpdateBlockQuery query);

    @Getter
    @Builder
    class ChargeBlockQuery {

        private String memberId;
        private String type;
        public String orderId;
        private Integer blockQuantity;

        public static ChargeBlockQuery toQuery(String memberId, String type, BlockRequest request) {
            return ChargeBlockQuery.builder()
                .type(type)
                .memberId(memberId)
                .orderId(request.getOrderId())
                .blockQuantity(request.getBlockQuantity())
                .build();
        }
    }

    @Getter
    @Builder
    class UpdateBlockQuery {

        private String memberId;
        private String type;
        private Integer blockQuantity;

        public static UpdateBlockQuery toQuery(String memberId, BlockRequest request) {
            return UpdateBlockQuery.builder()
                .type(request.getType())
                .blockQuantity(request.getBlockQuantity())
                .memberId(memberId)
                .build();
        }
    }

    @Getter
    @AllArgsConstructor
    class FindBlockQuery {

        private String memberId;
    }
}
