package com.blockpage.blockservice.adaptor.external.kakao.response;

import lombok.Getter;

@Getter
public class ApprovedCancelAmount {

    private Integer total;
    private Integer tax_free;
    private Integer vat;
    private Integer point;
    private Integer discount;
    private Integer green_deposit;
}
