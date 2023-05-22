package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import java.util.List;
public interface PaymentPersistencePort {

    PaymentEntityDto savePaymentRecord(PaymentEntityDto paymentEntityDto);
    PaymentEntityDto getPaymentByOrderId(String orderId);
    List<PaymentEntityDto> getBlockLossType(Long memberId);
    List<PaymentEntityDto> getBlockGainType(Long memberId);

}
