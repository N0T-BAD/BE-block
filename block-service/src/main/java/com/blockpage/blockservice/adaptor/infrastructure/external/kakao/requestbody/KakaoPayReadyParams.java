package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.configuration.KakaoPayRedirectUrl;
import com.blockpage.blockservice.application.port.in.PaymentUseCase.PaymentQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KakaoPayReadyParams {

    private String cid;
    private Integer tax_free_amount;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private Integer quantity;
    private Integer total_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;

    public static KakaoPayReadyParams addEssentialParams(PaymentQuery query, String orderNumber) {
        return KakaoPayReadyParams.builder()
            .cid("TC0ONETIME")
            .approval_url(KakaoPayRedirectUrl.KAKAO_APPROVAL_URL.getUrl())
            .cancel_url(KakaoPayRedirectUrl.KAKAO_CANCEL_URL.getUrl())
            .fail_url(KakaoPayRedirectUrl.KAKAO_FAIL_URL.getUrl())
            .tax_free_amount(0)
            .partner_order_id(orderNumber)
            .item_name(query.getItemName())
            .quantity(query.getBlockQuantity())
            .partner_user_id(query.getMemberId().toString())
            .total_amount(query.getTotalAmount())
            .build();
    }
}
