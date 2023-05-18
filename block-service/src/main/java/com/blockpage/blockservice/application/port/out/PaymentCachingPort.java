package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.service.PaymentService.PaymentReceiptDto;

public interface PaymentCachingPort {
    void savePaymentReceipt(PaymentReceiptDto paymentReceiptDto);
    PaymentReceiptDto getPaymentReceiptByMemberId(String memberId);

}
