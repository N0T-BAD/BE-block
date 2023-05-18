package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayRefundParams;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayRefundDto;

public interface PaymentRequestPort {

    KakaoPayReadyDto ready(KakaoPayReadyParams kakaoPayReadyParams);
    KakaoPayApproveDto approval(KakaoPayApprovalParams kakaoPayApprovalParams);
    KakaoPayRefundDto cancel(KakaoPayRefundParams kakaoPayRefundParams);
}
