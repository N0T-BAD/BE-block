package com.blockpage.blockservice.adaptor.external.kakao.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoPayRefundResponse {

    private String aid;
    private String tid;
    private String cid;
    private String status;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private ApprovedCancelAmount approved_cancel_amount;
    private CanceledAmount canceled_amount;
    private CancelAvailableAmount cancel_available_amount;
    private String item_name;
    private String item_code;
    private Integer quantity;
    private LocalDateTime created_at;
    private LocalDateTime approved_at;
    private LocalDateTime canceled_at;
    private String payload;

}
