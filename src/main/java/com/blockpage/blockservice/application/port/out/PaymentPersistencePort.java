package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import java.util.List;
public interface PaymentPersistencePort {

    void savePaymentRecord(PaymentEntityDto paymentEntityDto);
    PaymentEntityDto getPaymentByOrderId(String orderId);
    List<PaymentEntityDto> getBlockLossType(String memberId);
    List<PaymentEntityDto> getBlockGainType(String memberId);

}
