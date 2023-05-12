package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.PaymentHistoryType;
import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.view.PaymentHistoryView;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payment-histories")
public class PaymentController {

    /**
     * Mock Data 작업중 (서비스 로직 없음)
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getOrderHistory(@RequestParam String target, @RequestParam(required = false) String type) {

        switch (target) {
            case "member": {
                List<PaymentHistoryView> paymentHistoryViews = new ArrayList<>();
                if (type.equals("gain")) {
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 100, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 200, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 1000, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 2, BlockGainType.ATTENDANCE));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 100, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 100, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 2, BlockGainType.ATTENDANCE));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 100, BlockGainType.CASH));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 2, BlockGainType.GAME));
                    paymentHistoryViews.add(new PaymentHistoryView(1L, PaymentHistoryType.GAIN, 100, BlockGainType.CASH));
                } else {
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 2, BlockLossType.EPISODE_BM, 1L));
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 2, BlockLossType.EPISODE_BM, 2L));
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 2, BlockLossType.EPISODE_BM, 3L));
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 100, BlockLossType.PROFILE_SKIN, 100L));
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 1000, BlockLossType.PROFILE_SKIN, 1L));
                    paymentHistoryViews.add(
                        new PaymentHistoryView(1L, PaymentHistoryType.LOSS, 2, BlockLossType.EPISODE_BM, 44L));
                }
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(paymentHistoryViews));
            }

            case "author": {
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(new PaymentHistoryView(1L, 502328, BlockLossType.EPISODE_BM, 1L, 500000000L)));
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse("잘못된 요청 입니다."));
    }

}
