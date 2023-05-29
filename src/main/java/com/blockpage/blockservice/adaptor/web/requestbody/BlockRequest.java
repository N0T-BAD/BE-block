package com.blockpage.blockservice.adaptor.web.requestbody;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlockRequest {

    private Integer blockQuantity;
    private String type;
    private String orderId;

    private Integer episodeNumber;
    private String webtoonTitle;
}
