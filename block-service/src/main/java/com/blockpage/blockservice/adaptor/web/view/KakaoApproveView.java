package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoApproveView {

    private String userId;
    private String orderId;

    private String paymentMethodType;
    private Integer amount;

    private String itemName;
    private int quantity;

    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

    public KakaoApproveView(KakaoPayApproveDto dto) {
        this.userId = dto.getPartner_user_id();
        this.orderId = dto.getPartner_order_id();
        this.paymentMethodType = dto.getPayment_method_type();
        this.amount = dto.getAmount();
        this.itemName = dto.getItem_name();
        this.quantity = dto.getQuantity();
        this.createdAt = dto.getCreated_at();
        this.approvedAt = dto.getApproved_at();
    }
}
