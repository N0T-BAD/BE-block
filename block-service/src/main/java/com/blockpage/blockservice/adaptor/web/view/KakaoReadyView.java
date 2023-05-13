package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoReadyView {

    private String tid;
    private String next_redirect_pc_url;

    public KakaoReadyView(KakaoPayReadyDto kakaoPayReadyDto) {
        this.tid = kakaoPayReadyDto.getTid();
        this.next_redirect_pc_url = kakaoPayReadyDto.getNext_redirect_pc_url();
    }
}
