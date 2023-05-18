package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody;

import com.blockpage.blockservice.application.port.in.BlockUseCase.KakaoApproveQuery;
import com.blockpage.blockservice.application.service.BlockService.PaymentReceiptDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KakaoPayApprovalParams {

    private String cid;
    private String tid;
    private String pg_token;
    private String partner_order_id;
    private String partner_user_id;

    public static KakaoPayApprovalParams addEssentialParams(KakaoApproveQuery query, PaymentReceiptDto receipt) {

        return KakaoPayApprovalParams.builder()
            .cid("TC0ONETIME")
            .tid(receipt.getTid())
            .partner_order_id(receipt.getOrderId())
            .pg_token(query.getPgToken())
            .partner_user_id(query.getMemberId().toString())
            .build();
    }
}
