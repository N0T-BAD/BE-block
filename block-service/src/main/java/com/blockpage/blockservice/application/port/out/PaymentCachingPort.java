package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.service.BlockService.PaymentOutDto;

public interface PaymentCachingPort {
    void savePaymentReceipt(PaymentOutDto paymentOutDto);
    PaymentOutDto getPaymentReceiptByMemberId(String memberId);

}
