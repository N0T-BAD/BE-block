package com.blockpage.blockservice.adaptor.infrastructure.mysql.persistence;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.repository.PaymentRepository;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.service.PaymentService;
import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdaptor implements PaymentPersistencePort {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentEntityDto savePaymentRecord(PaymentService.PaymentEntityDto paymentEntityDto) {
        PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.toEntity(paymentEntityDto));
        return PaymentEntityDto.toDtoFromEntity(paymentEntity);
    }

    @Override
    public PaymentEntityDto getPaymentByOrderId(String orderId) {
        Optional<PaymentEntity> paymentEntity = paymentRepository.findByOrderId(orderId);
        return PaymentEntityDto.toDtoFromEntity(paymentEntity.get());
    }

    @Override
    public List<PaymentEntityDto> getBlockGainType(Long memberId) {
        List<PaymentEntity> paymentEntityList = paymentRepository.findByMemberIdAndBlockGainTypeIsNotNull(memberId);
        return paymentEntityList.stream()
            .map(PaymentEntityDto::toDtoFromEntity)
            .toList();
    }

    @Override
    public List<PaymentEntityDto> getBlockLossType(Long memberId) {
        List<PaymentEntity> paymentEntityList = paymentRepository.findByMemberIdAndBlockLossTypeIsNotNull(memberId);
        return paymentEntityList.stream()
            .map(PaymentEntityDto::toDtoFromEntity)
            .toList();
    }
}
