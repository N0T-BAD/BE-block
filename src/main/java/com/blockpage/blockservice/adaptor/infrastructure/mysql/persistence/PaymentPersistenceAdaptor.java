package com.blockpage.blockservice.adaptor.infrastructure.mysql.persistence;

import static com.blockpage.blockservice.exception.ErrorCode.*;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.repository.PaymentRepository;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import com.blockpage.blockservice.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdaptor implements PaymentPersistencePort {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void savePaymentRecord(PaymentEntityDto paymentEntityDto) {
        paymentRepository.save(PaymentEntity.toEntity(paymentEntityDto));
    }

    @Override
    @Transactional
    public PaymentEntityDto getPaymentByOrderId(String orderId) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId)
            .orElseThrow(
                () -> new BusinessException(NO_EXISTENCE_ORDER_ID_ERROR.getMessage(), NO_EXISTENCE_ORDER_ID_ERROR.getHttpStatus()));
        return PaymentEntityDto.toDtoFromEntity(paymentEntity);
    }

    @Override
    @Transactional
    public void updateValidStatePaymentRecord(String orderId) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId)
            .orElseThrow(
                () -> new BusinessException(NO_EXISTENCE_ORDER_ID_ERROR.getMessage(), NO_EXISTENCE_ORDER_ID_ERROR.getHttpStatus()));
        paymentEntity.updateValidState();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentEntityDto> getBlockGainType(String memberId) {
        List<PaymentEntity> paymentEntityList = paymentRepository.findByMemberIdAndBlockGainTypeNot(memberId, BlockGainType.NONE);
        return paymentEntityList.stream()
            .map(PaymentEntityDto::toDtoFromEntity)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentEntityDto> getBlockLossType(String memberId) {
        List<PaymentEntity> paymentEntityList = paymentRepository.findByMemberIdAndBlockLossTypeNot(memberId, BlockLossType.NONE);
        return paymentEntityList.stream()
            .map(PaymentEntityDto::toDtoFromEntity)
            .toList();
    }
}
