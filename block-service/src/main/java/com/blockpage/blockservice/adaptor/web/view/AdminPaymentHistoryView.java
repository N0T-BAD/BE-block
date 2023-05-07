package com.blockpage.blockservice.adaptor.web.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class AdminPaymentHistoryView {

    private Long cashBlocks;
    private Long attendanceBlocks;
    private Long gameBlocks;
    private Long totalBlocks;
    private Long totalIncome;
    private List<PaymentHistoryView> paymentHistoryViews;

    public AdminPaymentHistoryView(Long cashBlocks, Long attendanceBlocks, Long gameBlocks, Long totalBlocks, Long totalIncome,
        List<PaymentHistoryView> paymentHistoryViews) {
        this.cashBlocks = cashBlocks;
        this.attendanceBlocks = attendanceBlocks;
        this.gameBlocks = gameBlocks;
        this.totalBlocks = totalBlocks;
        this.totalIncome = totalIncome;
        this.paymentHistoryViews = paymentHistoryViews;
    }
}
