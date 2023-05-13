package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.view.TradingRecordView;
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
@RequestMapping("/v1/views/trading-record")
public class SettlementCreatorController {

    /**
     * Mock Data 작업중 (서비스 로직 없음) 작가(creator)의 정산
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getOrderHistory(@RequestParam String target, @RequestParam(required = false) String type) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new TradingRecordView(1L, 502328, BlockLossType.EPISODE_BM, 1L, 500000000L)));

    }

}
