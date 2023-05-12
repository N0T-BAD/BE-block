package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;

public interface PaymentPort {

    KakaoPayReadyDto ready(KakaoPayReadyParams kakaoPayReadyParams);

    KakaoPayApproveDto approval(KakaoPayApprovalParams kakaoPayApprovalParams);

}
