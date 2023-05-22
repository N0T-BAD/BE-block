package com.blockpage.blockservice.adaptor.web.requestbody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest {

    private Integer blockQuantity;

    //kakaoReadySpec
    private String itemName;
    private Integer totalAmount;

    //kakaoApproveSpec
    private String pgToken;

    //refund
    private String orderId;
}
