package com.blockpage.blockservice.adaptor.external.kakao.response;

import lombok.Getter;

@Getter
public class KakaoPayReadyResponse {
    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String created_at;
}
