package com.blockpage.blockservice.adaptor.external.kakao.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KakaoOpenFeignConfig {

    @Value("${kakao.pay.api.key}")
    private String kakaoPayApiKey;

    @Value("${kakao.pay.api.content-type}")
    private String kakaoPayApiContentType;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new KakaoOpenFeignHeaderInterceptor(kakaoPayApiKey, kakaoPayApiContentType);
    }
}
