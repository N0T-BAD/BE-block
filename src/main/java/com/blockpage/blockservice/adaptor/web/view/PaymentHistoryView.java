package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.application.service.PaymentService.PaymentHistoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class PaymentHistoryView {

    private String memberId;

    private Integer blockQuantity;
    private Integer totalAmount;

    private String paymentTime;

    private String blockGainType;
    private String blockLossType;

    private String episodeBMDetail;

    private String orderId;

    private Boolean validState;

    private Boolean expiredState;

    public PaymentHistoryView(PaymentHistoryDto paymentHistoryDto) {
        this.memberId = paymentHistoryDto.getMemberId();
        this.totalAmount = paymentHistoryDto.getTotalAmount() == 0 ? null : paymentHistoryDto.getTotalAmount();
        this.blockGainType =
            (paymentHistoryDto.getBlockGainType() != BlockGainType.NONE) ? paymentHistoryDto.getBlockGainType().getView() : null;
        this.blockLossType =
            paymentHistoryDto.getBlockLossType() != BlockLossType.NONE ? paymentHistoryDto.getBlockLossType().getView() : null;
        this.blockQuantity = paymentHistoryDto.getBlockQuantity();
        this.paymentTime = paymentHistoryDto.getPaymentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.expiredState = paymentHistoryDto.getPaymentTime().plusDays(3).isBefore(LocalDateTime.now());
        this.episodeBMDetail =
            paymentHistoryDto.getEpisodeNumber() != null ? paymentHistoryDto.getWebtoonTitle() + " " + paymentHistoryDto.getEpisodeNumber()
                .toString() + "화" : null;
        this.orderId = paymentHistoryDto.getOrderId();
        this.validState = paymentHistoryDto.getValidState();
    }
}
