package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody;

import com.blockpage.blockservice.application.port.out.PaymentPersistencePort.PaymentEntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KakaoPayRefundParams {

    private String cid;
    private String tid;
    private Integer cancel_amount;
    private Integer cancel_tax_free_amount;

    public static KakaoPayRefundParams addEssentialParams(PaymentEntityDto dto) {
        return KakaoPayRefundParams.builder()
            .cid("TC0ONETIME")
            .cancel_tax_free_amount(0)
            .tid(dto.getTid())
            .cancel_amount(dto.getTotalAmount())
            .build();
    }
}
