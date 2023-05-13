package com.blockpage.blockservice.adaptor.web.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class AdminTradingRecordView {

    private Long cashBlocks;
    private Long attendanceBlocks;
    private Long gameBlocks;
    private Long totalBlocks;
    private Long totalIncome;
    private List<TradingRecordView> tradingRecordViews;

    public AdminTradingRecordView(Long cashBlocks, Long attendanceBlocks, Long gameBlocks, Long totalBlocks, Long totalIncome,
        List<TradingRecordView> tradingRecordViews) {
        this.cashBlocks = cashBlocks;
        this.attendanceBlocks = attendanceBlocks;
        this.gameBlocks = gameBlocks;
        this.totalBlocks = totalBlocks;
        this.totalIncome = totalIncome;
        this.tradingRecordViews = tradingRecordViews;
    }
}
