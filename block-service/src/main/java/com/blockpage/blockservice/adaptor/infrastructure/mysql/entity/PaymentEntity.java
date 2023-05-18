package com.blockpage.blockservice.adaptor.infrastructure.mysql.entity;

import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.PaymentType;
import com.blockpage.blockservice.application.service.PaymentService.PaymentEntityDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long memberId;
    @Column
    private String orderId;
    @Column
    private String tid;
    @Column
    private String itemName;
    @Column
    private String paymentCompany;
    @Column
    private Integer blockQuantity;
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column
    private LocalDateTime paymentTime;
    @Column
    private Integer totalAmount;

    public static PaymentEntity toEntity(PaymentEntityDto paymentEntityDto) {
        return PaymentEntity.builder()
            .memberId(Long.valueOf(paymentEntityDto.getMemberId()))
            .orderId(paymentEntityDto.getOrderId())
            .tid(paymentEntityDto.getTid())
            .itemName(paymentEntityDto.getItemName())
            .blockQuantity(Integer.valueOf(paymentEntityDto.getQuantity()))
            .totalAmount(Integer.valueOf(paymentEntityDto.getTotalAmount()))
            .paymentType(PaymentType.valueOf(paymentEntityDto.getPaymentType()))
            .paymentTime(paymentEntityDto.getPaymentTime())
            .paymentCompany(paymentEntityDto.getPaymentCompany())
            .build();
    }
}
