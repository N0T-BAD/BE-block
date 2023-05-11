package com.blockpage.blockservice.adaptor.web.controller;


import com.blockpage.blockservice.adaptor.infrastructure.value.BlockLossType;
import com.blockpage.blockservice.adaptor.web.view.ApiWrapperResponse;
import com.blockpage.blockservice.adaptor.web.view.AdminPaymentHistoryView;
import com.blockpage.blockservice.adaptor.web.view.PaymentHistoryView;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/operations")
public class OperationController {

    /**
     * Mock Data 작업중 (서비스 로직 없음) block 도메인과 paymentHistory 도메인이 결합 되어야함.
     */
    @GetMapping
    public ResponseEntity<ApiWrapperResponse> getOrderHistory() {
        List<PaymentHistoryView> paymentHistoryViews = new ArrayList<>();
        paymentHistoryViews.add(new PaymentHistoryView(1L, 502328, BlockLossType.WEBTOON, 1L, 504619615L));
        paymentHistoryViews.add(new PaymentHistoryView(2L, 15615615, BlockLossType.WEBTOON, 2L, 1651561560L));
        paymentHistoryViews.add(new PaymentHistoryView(2L, 4542, BlockLossType.WEBTOON, 3L, 50452L));
        paymentHistoryViews.add(new PaymentHistoryView(4L, 45242, BlockLossType.WEBTOON, 4L, 50042524522L));
        paymentHistoryViews.add(new PaymentHistoryView(5L, 4564, BlockLossType.WEBTOON, 5L, 500250L));
        paymentHistoryViews.add(new PaymentHistoryView(6L, 567, BlockLossType.WEBTOON, 7L, 500000000L));
        AdminPaymentHistoryView result = new AdminPaymentHistoryView(10000000L, 8000L, 6000L, 10014000L, 8500000000L,
            paymentHistoryViews);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiWrapperResponse(result));
    }
}
