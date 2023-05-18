package com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response;


import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoPayApprovalResponse {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private String item_name;
    private String item_code;
    private int quantity;
    private LocalDateTime created_at;
    private LocalDateTime approved_at;
    private String payload;
}
