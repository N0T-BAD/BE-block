package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.configuration;

import com.blockpage.blockservice.exception.GlobalFeignClientErrorDecoder;
import feign.Logger.Level;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KakaoOpenFeignConfig {

    @Value("${kakao.pay.api.key}")
    private String kakaoPayApiKey;

    @Value("${kakao.pay.api.content-type}")
    private String kakaoPayApiContentType;

    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GlobalFeignClientErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(); //this(100, *SECONDS*.toMillis(1), 5);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new KakaoOpenFeignHeaderInterceptor(kakaoPayApiKey, kakaoPayApiContentType);
    }

}
