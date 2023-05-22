package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.PaymentService.PaymentResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentView {

    private String tid;
    private String nextRedirectPcUrl;

    private String userId;
    private String orderId;
    private String paymentMethod;
    private Integer totalAmount;
    private String itemName;
    private Integer blockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;

    public PaymentView(PaymentResponseDto paymentResponseDto) {
        this.tid = paymentResponseDto.getTid();
        this.nextRedirectPcUrl = paymentResponseDto.getNext_redirect_pc_url();
        this.userId = paymentResponseDto.getPartner_user_id();
        this.orderId = paymentResponseDto.getPartner_order_id();
        this.paymentMethod = paymentResponseDto.getPayment_method_type();
        this.totalAmount = paymentResponseDto.getAmount();
        this.itemName = paymentResponseDto.getItem_name();
        this.blockQuantity = paymentResponseDto.getQuantity();
        this.createdAt = paymentResponseDto.getCreated_at();
        this.approvedAt = paymentResponseDto.getApproved_at();
    }
}
