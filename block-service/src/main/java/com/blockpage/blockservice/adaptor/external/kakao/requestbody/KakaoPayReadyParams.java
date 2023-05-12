package com.blockpage.blockservice.adaptor.external.kakao.requestbody;

import static com.blockpage.blockservice.adaptor.external.kakao.configuration.KakaoPayRedirectUrl.KAKAO_APPROVAL_URL;
import static com.blockpage.blockservice.adaptor.external.kakao.configuration.KakaoPayRedirectUrl.KAKAO_CANCEL_URL;
import static com.blockpage.blockservice.adaptor.external.kakao.configuration.KakaoPayRedirectUrl.KAKAO_FAIL_URL;

import com.blockpage.blockservice.application.port.in.BlockUseCase.KakaoReadyQuery;
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

    public static KakaoPayReadyParams addEssentialParams(KakaoReadyQuery query, String orderNumber) {
        return KakaoPayReadyParams.builder()
            .cid("TC0ONETIME")
            .approval_url(KAKAO_APPROVAL_URL.getUrl())
            .cancel_url(KAKAO_CANCEL_URL.getUrl())
            .fail_url(KAKAO_FAIL_URL.getUrl())
            .tax_free_amount(0)
            .partner_order_id(orderNumber)
            .item_name(query.getItemName())
            .quantity(query.getQuantity())
            .partner_user_id(query.getMemberId().toString())
            .total_amount(query.getTotalAmount())
            .build();
    }
}
