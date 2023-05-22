package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayRefundParams;
import com.blockpage.blockservice.application.service.PaymentService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.PaymentService.KakaoPayReadyDto;
import com.blockpage.blockservice.application.service.PaymentService.KakaoPayRefundDto;

public interface PaymentRequestPort {

    KakaoPayReadyDto ready(KakaoPayReadyParams kakaoPayReadyParams);
    KakaoPayApproveDto approval(KakaoPayApprovalParams kakaoPayApprovalParams);
    KakaoPayRefundDto cancel(KakaoPayRefundParams kakaoPayRefundParams);
}
