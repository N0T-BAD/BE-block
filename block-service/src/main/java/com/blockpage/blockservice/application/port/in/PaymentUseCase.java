package com.blockpage.blockservice.application.port.in;

import com.blockpage.blockservice.adaptor.web.requestbody.PaymentRequest;

import com.blockpage.blockservice.application.service.PaymentService.PaymentHistoryDto;
import com.blockpage.blockservice.application.service.PaymentService.PaymentResponseDto;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface PaymentUseCase {

    PaymentResponseDto paymentQuery(PaymentQuery query);

    List<PaymentHistoryDto> paymentHistoryQuery(PaymentHistoryQuery query);

    @Getter
    @Builder
    class PaymentQuery {

        //common
        private CorpAndStep type;
        private Long memberId;
        //ready
        private String itemName;
        private Integer blockQuantity;
        private Integer totalAmount;
        //approve
        private String pgToken;
        //refund
        private String orderId;

        public static PaymentQuery toQuery(Long memberId, String type, PaymentRequest request) {
            return PaymentQuery.builder()
                .memberId(memberId)
                .type(CorpAndStep.findByValue(type))
                .itemName(request.getItemName())
                .blockQuantity(request.getBlockQuantity())
                .totalAmount(request.getTotalAmount())
                .pgToken(request.getPgToken())
                .orderId(request.getOrderId())
                .build();
        }
    }

    @Getter
    @Builder
    class PaymentHistoryQuery {

        private Long memberId;
        private String type;

        public static PaymentHistoryQuery toQuery(Long memberId, String type) {
            return PaymentHistoryQuery.builder()
                .memberId(memberId)
                .type(type)
                .build();
        }
    }

    @Getter
    @AllArgsConstructor
    enum CorpAndStep {
        KAKAO_PAY_READY(0, "kakao-ready"),
        KAKAO_PAY_APPROVE(1, "kakao-approve"),
        KAKAO_PAY_REFUND(2, "kakao-refund"),
        ;
        private int key;
        private String value;

        public static CorpAndStep findByValue(String value) {
            return Arrays.stream(CorpAndStep.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .get();
        }
    }
}
