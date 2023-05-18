package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.PaymentType;
import com.blockpage.blockservice.application.service.PaymentService;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public interface PaymentPersistencePort {

    PaymentEntityDto savePaymentRecord(PaymentService.PaymentEntityDto paymentEntityDto);
    PaymentEntityDto getPayment(String orderId);

    @Builder
    @Getter
    class PaymentEntityDto {

        private Long id;
        private Long memberId;
        private String orderId;
        private String tid;
        private String itemName;
        private Integer blockQuantity;
        private PaymentType paymentType;
        private LocalDateTime paymentTime;
        private String paymentCompany;
        private Integer totalAmount;

        public static PaymentEntityDto toDto(PaymentEntity paymentEntity) {
            return PaymentPersistencePort.PaymentEntityDto.builder()
                .id(paymentEntity.getId())
                .memberId(paymentEntity.getMemberId())
                .orderId(paymentEntity.getOrderId())
                .tid(paymentEntity.getTid())
                .itemName(paymentEntity.getItemName())
                .blockQuantity(paymentEntity.getBlockQuantity())
                .paymentType(paymentEntity.getPaymentType())
                .paymentTime(paymentEntity.getPaymentTime())
                .paymentCompany(paymentEntity.getPaymentCompany())
                .totalAmount(paymentEntity.getTotalAmount())
                .build();
        }
    }
}
