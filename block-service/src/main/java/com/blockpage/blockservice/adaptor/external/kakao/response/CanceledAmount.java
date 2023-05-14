package com.blockpage.blockservice.adaptor.external.kakao.response;

import lombok.Getter;

@Getter
public class CanceledAmount {

    private Integer total;
    private Integer tax_free;
    private Integer tax;
    private Integer point;
    private Integer discount;
    private Integer green_deposit;
}
