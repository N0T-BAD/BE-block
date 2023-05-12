package com.blockpage.blockservice.adaptor.external.kakao.controller;

import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayApprovalResponse;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.application.port.out.PaymentPort;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoPayController implements PaymentPort {

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
}
