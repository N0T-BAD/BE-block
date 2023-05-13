package com.blockpage.blockservice.adaptor.web.controller;


import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.view.AdminTradingRecordView;
import com.blockpage.blockservice.adaptor.web.view.TradingRecordView;
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
@RequestMapping("/v1/operations/trading-record")
public class SettlementAdminController {

    /**
     * Mock Data 작업중 (서비스 로직 없음) 관리자 정산을 위한 페이지
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getOrderHistory() {
        List<TradingRecordView> tradingRecordViews = new ArrayList<>();
        tradingRecordViews.add(new TradingRecordView(1L, 502328, BlockLossType.WEBTOON, 1L, 504619615L));
        tradingRecordViews.add(new TradingRecordView(2L, 15615615, BlockLossType.WEBTOON, 2L, 1651561560L));
        tradingRecordViews.add(new TradingRecordView(2L, 4542, BlockLossType.WEBTOON, 3L, 50452L));
        tradingRecordViews.add(new TradingRecordView(4L, 45242, BlockLossType.WEBTOON, 4L, 50042524522L));
        tradingRecordViews.add(new TradingRecordView(5L, 4564, BlockLossType.WEBTOON, 5L, 500250L));
        tradingRecordViews.add(new TradingRecordView(6L, 567, BlockLossType.WEBTOON, 7L, 500000000L));
        AdminTradingRecordView result = new AdminTradingRecordView(10000000L, 8000L, 6000L, 10014000L, 8500000000L,
            tradingRecordViews);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(result));
    }
}
