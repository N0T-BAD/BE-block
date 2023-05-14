package com.blockpage.blockservice.adaptor.infrastructure.mysql.persistence;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.repository.PaymentRepository;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.service.BlockService.PaymentDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdaptor implements PaymentPersistencePort {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentEntityDto savePaymentRecord(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.toEntity(paymentDto));
        return PaymentEntityDto.toDto(paymentEntity);
    }

    @Override
    public PaymentEntityDto getPayment(String orderId) {
        Optional<PaymentEntity> paymentEntity = paymentRepository.findByOrderId(orderId);
        return PaymentEntityDto.toDto(paymentEntity.get());
    }
}
