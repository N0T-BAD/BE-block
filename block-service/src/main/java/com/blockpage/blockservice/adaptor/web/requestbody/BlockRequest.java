package com.blockpage.blockservice.adaptor.web.requestbody;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlockRequest {

    private Integer quantity;

    //kakaoReadySpec
    private String itemName;
    private Integer totalAmount;

    //kakaoApproveSpec
    private String pgToken;

}
