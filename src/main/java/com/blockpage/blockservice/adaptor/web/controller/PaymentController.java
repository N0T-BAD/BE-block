package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.web.requestbody.PaymentRequest;
import com.blockpage.blockservice.adaptor.web.view.ApiResponse;

import com.blockpage.blockservice.adaptor.web.view.PaymentHistoryView;
import com.blockpage.blockservice.adaptor.web.view.PaymentView;
import com.blockpage.blockservice.application.port.in.PaymentUseCase;

import com.blockpage.blockservice.application.port.in.PaymentUseCase.PaymentQuery;
import com.blockpage.blockservice.application.service.PaymentService.PaymentHistoryDto;
import com.blockpage.blockservice.application.service.PaymentService.PaymentResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    private final long MEMBER_TEST_ID = 1L;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentView>> postPayment(
        @RequestParam String type,
        @RequestBody PaymentRequest paymentRequest
    ) {
        PaymentResponseDto paymentResponseDto = paymentUseCase.paymentQuery(PaymentQuery.toQuery(MEMBER_TEST_ID, type, paymentRequest));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse<>(new PaymentView(paymentResponseDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentHistoryView>>> getPayment(@RequestParam String type) {
        List<PaymentHistoryDto> paymentHistoryDtos = paymentUseCase.paymentHistoryQuery(
            PaymentUseCase.PaymentHistoryQuery.toQuery(MEMBER_TEST_ID, type));
        List<PaymentHistoryView> paymentHistoryViews = paymentHistoryDtos.stream()
            .map(PaymentHistoryView::new)
            .toList();
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse<>(paymentHistoryViews));
    }
}
