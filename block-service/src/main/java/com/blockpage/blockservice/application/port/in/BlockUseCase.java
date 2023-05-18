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

        private Long memberId;
        private String type;
        public String orderId;
        private Integer blockQuantity;

        public static ChargeBlockQuery toQuery(Long memberId, String type, BlockRequest request) {
            return ChargeBlockQuery.builder()
                .type(type)
                .memberId(memberId)
                .orderId(request.getOrderId())
                .blockQuantity(request.getBlockQuantity())
                .build();
        }
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

    @Getter
    @AllArgsConstructor
    class KakaoReadyQuery {

        private Long memberId;
        private String itemName;
        private Integer quantity;
        private Integer totalAmount;
    }

    @Getter
    @AllArgsConstructor
    class KakaoApproveQuery {

        private Long memberId;
        private String pgToken;
    }
}
