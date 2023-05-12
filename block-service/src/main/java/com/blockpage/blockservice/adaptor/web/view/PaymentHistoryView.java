package com.blockpage.blockservice.adaptor.web.view;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.PaymentHistoryType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class PaymentHistoryView {

    //common
    private Long memberId;
    private Integer blockQuantity;
    private productDescription productDescription;

    //member
    private PaymentHistoryType paymentHistoryType;
    private BlockGainType blockGainType;

    //creator
    private Long won;

    //member-loss
    public PaymentHistoryView(Long memberId, PaymentHistoryType paymentHistoryType, Integer blockQuantity, BlockLossType blockLossType, Long productId) {
        this.memberId = memberId;
        this.paymentHistoryType = paymentHistoryType;
        this.blockQuantity = blockQuantity;
        this.productDescription = new productDescription(blockLossType.toString(), productId);

    }

    //member-obtain
    public PaymentHistoryView(Long memberId, PaymentHistoryType paymentHistoryType, Integer blockQuantity, BlockGainType blockGainType) {
        this.memberId = memberId;
        this.paymentHistoryType = paymentHistoryType;
        this.blockQuantity = blockQuantity;
        this.blockGainType = blockGainType;
    }

    //author-episode
    public PaymentHistoryView(Long memberId, Integer blockQuantity,BlockLossType blockLossType, Long productId, Long won) {
        this.memberId = memberId;
        this.blockQuantity = blockQuantity;
        this.productDescription = new productDescription(blockLossType.toString(), productId);
        this.won = won;
    }


    @Data
    @AllArgsConstructor
    @JsonInclude(Include.NON_NULL)
    private class productDescription {

        private String productType;
        private Long productId;

    }
}
