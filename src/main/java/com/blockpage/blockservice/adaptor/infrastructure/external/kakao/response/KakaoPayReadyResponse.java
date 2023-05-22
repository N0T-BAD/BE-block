package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoPayReadyResponse {
    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private LocalDateTime created_at;
}
