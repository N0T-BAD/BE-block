package com.blockpage.blockservice.application.port.in;

import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BlockUseCase {

    BlockQueryDto findAllBlock(FindBlockQuery findBlockQuery);

    void createBlock(ChargeBlockQuery query);

    void consumeBlock(UpdateBlockQuery query);

    KakaoPayReadyDto kakaoPayReady(KakaoReadyQuery kakaoReadyQuery);

    KakaoPayApproveDto kakaoPayApprove(KakaoApproveQuery kakaoApproveQuery);

    void refundBlock(refundBlockQuery refundBlockQuery);


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
    @Getter
    @AllArgsConstructor
    class refundBlockQuery {

        private Long memberId;
        private String orderId;
    }
}
