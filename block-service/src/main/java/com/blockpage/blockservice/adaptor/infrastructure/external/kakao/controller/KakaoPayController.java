package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.controller;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayRefundParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayApprovalResponse;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayRefundResponse;
import com.blockpage.blockservice.application.port.out.PaymentRequestPort;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayRefundDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoPayController implements PaymentRequestPort {

    private final KakaoPayOpenFeign kakaoPayOpenFeign;

    @Override
    public KakaoPayReadyDto ready(KakaoPayReadyParams kakaoPayReadyParams) {
        KakaoPayReadyResponse response = kakaoPayOpenFeign.ready(kakaoPayReadyParams);
        return new KakaoPayReadyDto(response);
    }

    @Override
    public KakaoPayApproveDto approval(KakaoPayApprovalParams kakaoPayApprovalParams) {
        KakaoPayApprovalResponse response = kakaoPayOpenFeign.approval(kakaoPayApprovalParams);
        return new KakaoPayApproveDto(response);
    }

    @Override
    public KakaoPayRefundDto cancel(KakaoPayRefundParams kakaoPayRefundParams) {
        KakaoPayRefundResponse response = kakaoPayOpenFeign.refund(kakaoPayRefundParams);
        return new KakaoPayRefundDto(response);
    }
}
