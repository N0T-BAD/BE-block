package com.blockpage.blockservice.adaptor.external.kakao.controller;

import com.blockpage.blockservice.adaptor.external.kakao.configuration.KakaoOpenFeignConfig;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayReadyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "kakaopay-service", url = "${kakao.pay.api.url}", configuration = KakaoOpenFeignConfig.class)
public interface KakaoPayOpenFeign {

    @PostMapping(value = "/v1/payment/ready")
    KakaoPayReadyResponse ready(@SpringQueryMap KakaoPayReadyParams kakaoPayReadyParams);

//    @PostMapping(value = "/v1/payment/approve")
//    KakaoPayApprovalResponse approval(@SpringQueryMap KakaoPayApprovalParams kakaoPayApprovalParams);

}
