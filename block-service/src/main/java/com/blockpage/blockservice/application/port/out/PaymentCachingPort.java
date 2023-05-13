package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.service.BlockService.PaymentReceiptDto;

public interface PaymentCachingPort {
    void savePaymentReceipt(PaymentReceiptDto paymentReceiptDto);
    PaymentReceiptDto getPaymentReceiptByMemberId(String memberId);

}
