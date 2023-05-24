package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.application.service.PaymentService.PaymentHistoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class PaymentHistoryView {

    private String memberId;

    private String itemName;
    private Integer blockQuantity;
    private Integer totalAmount;

    private LocalDateTime paymentTime;

    private String blockGainType;
    private String blockLossType;

    public PaymentHistoryView(PaymentHistoryDto paymentHistoryDto) {
        this.memberId = paymentHistoryDto.getMemberId();
        this.itemName = paymentHistoryDto.getItemName();
        this.blockQuantity = paymentHistoryDto.getBlockQuantity();
        this.totalAmount = paymentHistoryDto.getTotalAmount();
        this.paymentTime = paymentHistoryDto.getPaymentTime();
        this.blockGainType = paymentHistoryDto.getBlockGainType() != null ? paymentHistoryDto.getBlockGainType().toString() : null;
        this.blockLossType = paymentHistoryDto.getBlockLossType() != null ? paymentHistoryDto.getBlockLossType().toString() : null;
    }
}
