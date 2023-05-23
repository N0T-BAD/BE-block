package com.blockpage.blockservice.application.service;

import static com.blockpage.blockservice.exception.ErrorCode.*;

import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.requestbody.KakaoPayRefundParams;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayApprovalResponse;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.adaptor.infrastructure.external.kakao.response.KakaoPayRefundResponse;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.PaymentEntity;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockGainType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.BlockLossType;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.value.PaymentMethod;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.UpdateBlockQuery;
import com.blockpage.blockservice.application.port.in.PaymentUseCase;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.application.port.out.PaymentCachingPort;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.port.out.PaymentRequestPort;
import com.blockpage.blockservice.domain.Block;
import com.blockpage.blockservice.exception.BusinessException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService implements PaymentUseCase {

    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentRequestPort paymentRequestPort;
    private final PaymentCachingPort paymentCachingPort;
    private final BlockPersistencePort blockPersistencePort;

    @Override
    @Transactional
    public PaymentResponseDto paymentQuery(PaymentQuery query) {
        switch (query.getType()) {
            case KAKAO_PAY_READY -> {
                String orderNumber = createOrderNumber(query.getMemberId());
                KakaoPayReadyParams kakaoPayReadyParams = KakaoPayReadyParams.addEssentialParams(query, orderNumber);
                KakaoPayReadyDto response = paymentRequestPort.ready(kakaoPayReadyParams);
                paymentCachingPort.savePaymentReceipt(new PaymentReceiptDto(query, response, orderNumber));
                return PaymentResponseDto.initFromKakaoReadyDto(response);
            }
            case KAKAO_PAY_APPROVE -> {
                PaymentReceiptDto receipt = paymentCachingPort.getPaymentReceiptByMemberId(query.getMemberId().toString());
                KakaoPayApprovalParams kakaoPayApprovalParams = KakaoPayApprovalParams.addEssentialParams(query, receipt);
                KakaoPayApproveDto response = paymentRequestPort.approval(kakaoPayApprovalParams);
                paymentPersistencePort.savePaymentRecord(PaymentEntityDto.initForKakaoApprove(receipt, response));
                return PaymentResponseDto.initFromKakaoApproveDto(response);
            }
            case KAKAO_PAY_REFUND -> {
                PaymentEntityDto paymentEntityDto = paymentPersistencePort.getPaymentByOrderId(query.getOrderId());
                Block orderedBlock = blockPersistencePort.getBlockByOrderId(query.getOrderId());
                if (orderedBlock.getBlockQuantity().equals(paymentEntityDto.getBlockQuantity())) {
                    KakaoPayRefundParams kakaoPayRefundParams = KakaoPayRefundParams.addEssentialParams(paymentEntityDto);
                    KakaoPayRefundDto kakaoPayRefundDto = paymentRequestPort.cancel(kakaoPayRefundParams);
                    blockPersistencePort.deleteBlockByOrderId(kakaoPayRefundDto.getOrderId());
                    paymentPersistencePort.savePaymentRecord(PaymentEntityDto.initForKakaoRefund(paymentEntityDto));
                    return PaymentResponseDto.initFromKakaoRefundDto();
                } else {
                    throw new BusinessException(INCONSISTENT_BLOCK_QUANTITY.getMessage(), INCONSISTENT_BLOCK_QUANTITY.getHttpStatus());
                }
            }
            default -> throw new BusinessException(WRONG_TYPE_FOR_PAYMENT_ERROR.getMessage(), WRONG_TYPE_FOR_PAYMENT_ERROR.getHttpStatus());
        }
    }

    @Override
    public List<PaymentHistoryDto> paymentHistoryQuery(PaymentHistoryQuery query) {
        List<PaymentEntityDto> paymentEntityDtos;
        switch (PaymentType.findByValue(query.getType())) {
            case GAIN -> paymentEntityDtos = paymentPersistencePort.getBlockGainType(query.getMemberId());
            case LOSS -> paymentEntityDtos = paymentPersistencePort.getBlockLossType(query.getMemberId());
            default -> throw new BusinessException(WRONG_TYPE_FOR_PAYMENT_ERROR.getMessage(), WRONG_TYPE_FOR_PAYMENT_ERROR.getHttpStatus());
        }
        return paymentEntityDtos.stream()
            .map(PaymentHistoryDto::toHistoryDto)
            .toList();
    }

    @Getter
    @AllArgsConstructor
    enum PaymentType {
        GAIN(0, "gain"),
        LOSS(0, "loss"),
        ;
        int key;
        String value;

        public static PaymentType findByValue(String value) {
            return Arrays.stream(PaymentType.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .get();
        }
    }

    @Getter
    @Builder
    public static class PaymentHistoryDto {

        private Long memberId;

        private String itemName;
        private Integer blockQuantity;
        private Integer totalAmount;

        private LocalDateTime paymentTime;

        private BlockGainType blockGainType;
        private BlockLossType blockLossType;

        public static PaymentHistoryDto toHistoryDto(PaymentEntityDto paymentEntityDto) {
            return PaymentHistoryDto.builder()
                .memberId(paymentEntityDto.getMemberId())
                .itemName(paymentEntityDto.getItemName())
                .blockQuantity(paymentEntityDto.getBlockQuantity())
                .totalAmount(paymentEntityDto.getTotalAmount())
                .paymentTime(paymentEntityDto.getPaymentTime())
                .blockGainType(paymentEntityDto.getBlockGainType())
                .blockLossType(paymentEntityDto.getBlockLossType())
                .build();
        }
    }

    @Getter
    @Builder
    public static class PaymentResponseDto {

        private String tid;
        private LocalDateTime created_at;

        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;

        private String cid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Integer amount;
        private String item_name;
        private Integer quantity;
        private LocalDateTime approved_at;

        public static PaymentResponseDto initFromKakaoReadyDto(KakaoPayReadyDto response) {
            return PaymentResponseDto.builder()
                .tid(response.getTid())
                .created_at(response.getCreated_at())
                .next_redirect_mobile_url(response.getNext_redirect_mobile_url())
                .next_redirect_pc_url(response.getNext_redirect_pc_url())
                .build();
        }

        public static PaymentResponseDto initFromKakaoApproveDto(KakaoPayApproveDto response) {
            return PaymentResponseDto.builder()
                .tid(response.getTid())
                .created_at(response.getCreated_at())
                .cid(response.getCid())
                .partner_order_id(response.getPartner_order_id())
                .partner_user_id(response.getPartner_user_id())
                .payment_method_type(response.getPayment_method_type())
                .amount(response.getAmount())
                .item_name(response.getItem_name())
                .quantity(response.getQuantity())
                .approved_at(response.getApproved_at())
                .build();
        }

        public static PaymentResponseDto initFromKakaoRefundDto() {
            return PaymentResponseDto.builder()
                .build();
        }
    }

    @Getter
    @Builder
    public static class PaymentEntityDto {

        private Long memberId;
        private String tid;
        private String orderId;

        private String itemName;
        private Integer blockQuantity;
        private Integer totalAmount;
        private String paymentCompany;

        private String paymentMethod;
        private LocalDateTime paymentTime;

        private BlockGainType blockGainType;
        private BlockLossType blockLossType;

        public static PaymentEntityDto initForKakaoApprove(PaymentReceiptDto receipt, KakaoPayApproveDto response) {
            return PaymentEntityDto.builder()
                .tid(receipt.getTid())
                .memberId(Long.parseLong(receipt.getMemberId()))
                .orderId(receipt.getOrderId())
                .itemName(receipt.getItemName())
                .blockQuantity(Integer.parseInt(receipt.getQuantity()))
                .totalAmount(Integer.parseInt(receipt.getTotalAmount()))
                .paymentTime(response.getApproved_at())
                .paymentMethod(response.getPayment_method_type())
                .blockGainType(BlockGainType.CASH)
                .blockLossType(BlockLossType.NONE)
                .paymentCompany("kakao")
                .build();
        }

        public static PaymentEntityDto initForKakaoRefund(PaymentEntityDto entityDto) {
            return PaymentEntityDto.builder()
                .tid(entityDto.getTid())
                .memberId(entityDto.getMemberId())
                .orderId(entityDto.getOrderId())
                .itemName(entityDto.getItemName())
                .blockQuantity(entityDto.getBlockQuantity())
                .totalAmount(entityDto.getTotalAmount())
                .paymentTime(entityDto.getPaymentTime())
                .paymentMethod(entityDto.getPaymentMethod())
                .blockGainType(BlockGainType.NONE)
                .blockLossType(BlockLossType.REFUND)
                .paymentCompany("kakao")
                .build();
        }

        public static PaymentEntityDto toDtoFromEntity(PaymentEntity entity) {
            return PaymentEntityDto.builder()
                .tid(entity.getTid())
                .memberId(entity.getMemberId())
                .orderId(entity.getOrderId())
                .itemName(entity.getItemName())
                .blockQuantity(entity.getBlockQuantity())
                .totalAmount(entity.getTotalAmount())
                .paymentTime(entity.getPaymentTime())
                .paymentMethod(entity.getPaymentMethod().toString())
                .blockGainType(entity.getBlockGainType())
                .blockLossType(entity.getBlockLossType())
                .paymentCompany(entity.getPaymentCompany())
                .build();
        }

        public static PaymentEntityDto initForGameAndAttendance(ChargeBlockQuery query) {
            return PaymentEntityDto.builder()
                .tid("none")
                .memberId(query.getMemberId())
                .orderId(createOrderNumber(query.getMemberId()))
                .itemName(query.getType())
                .blockQuantity(query.getBlockQuantity())
                .totalAmount(0)
                .paymentTime(LocalDateTime.now())
                .paymentMethod(PaymentMethod.FREE.toString())
                .blockGainType(BlockGainType.findByValue(query.getType()))
                .blockLossType(BlockLossType.NONE)
                .paymentCompany("blockPage")
                .build();
        }

        public static PaymentEntityDto initForInternalService(UpdateBlockQuery query) {
            return PaymentEntityDto.builder()
                .tid("none")
                .memberId(query.getMemberId())
                .orderId(createOrderNumber(query.getMemberId()))
                .itemName(query.getType())
                .blockQuantity(query.getBlockQuantity())
                .totalAmount(0)
                .paymentTime(LocalDateTime.now())
                .paymentMethod(PaymentMethod.BLOCK.toString())
                .blockGainType(BlockGainType.NONE)
                .blockLossType(BlockLossType.findByValue(query.getType()))
                .paymentCompany("blockPage")
                .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class PaymentReceiptDto {

        private String memberId;
        private String tid;
        private String orderId;

        private String itemName;
        private String quantity;
        private String totalAmount;

        public PaymentReceiptDto(PaymentQuery query, KakaoPayReadyDto dto, String orderNumber) {
            this.orderId = orderNumber;
            this.tid = dto.getTid();
            this.memberId = query.getMemberId().toString();
            this.itemName = query.getItemName();
            this.quantity = query.getBlockQuantity().toString();
            this.totalAmount = query.getTotalAmount().toString();
        }
    }

    @Getter
    public static class KakaoPayReadyDto {

        private String tid;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private LocalDateTime created_at;

        public KakaoPayReadyDto(KakaoPayReadyResponse response) {
            this.tid = response.getTid();
            this.next_redirect_mobile_url = response.getNext_redirect_mobile_url();
            this.next_redirect_pc_url = response.getNext_redirect_pc_url();
            this.created_at = response.getCreated_at();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class KakaoPayApproveDto {

        private String tid;
        private String cid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Integer amount;
        private String item_name;
        private int quantity;
        private LocalDateTime created_at;
        private LocalDateTime approved_at;

        public KakaoPayApproveDto(KakaoPayApprovalResponse response) {
            this.tid = response.getTid();
            this.cid = response.getCid();
            this.partner_order_id = response.getPartner_order_id();
            this.partner_user_id = response.getPartner_user_id();
            this.payment_method_type = response.getPayment_method_type();
            this.amount = response.getAmount().getTotal();
            this.item_name = response.getItem_name();
            this.quantity = response.getQuantity();
            this.created_at = response.getCreated_at();
            this.approved_at = response.getApproved_at();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class KakaoPayRefundDto {

        private String tid;
        private String status;
        private String orderId;
        private String memberId;
        private Integer amount;
        private Integer approvedCancelAmount;
        private Integer canceledAmount;
        private String itemName;
        private Integer quantity;
        private LocalDateTime createdAt;
        private LocalDateTime approvedAt;
        private LocalDateTime canceledAt;

        public KakaoPayRefundDto(KakaoPayRefundResponse response) {
            this.tid = response.getTid();
            this.status = response.getStatus();
            this.orderId = response.getPartner_order_id();
            this.memberId = response.getPartner_user_id();
            this.amount = response.getAmount().getTotal();
            this.approvedCancelAmount = response.getApproved_cancel_amount().getTotal();
            this.canceledAmount = response.getCanceled_amount().getTotal();
            this.itemName = response.getItem_name();
            this.quantity = response.getQuantity();
            this.createdAt = response.getCreated_at();
            this.approvedAt = response.getApproved_at();
            this.canceledAt = response.getCanceled_at();
        }
    }

    public static String createOrderNumber(Long memberId) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        String yyyyMmDd = LocalDate.now().toString().replace("-", "");
        String randomNumbers = Stream.generate(() -> random.nextInt(1, 10))
            .limit(6)
            .map(String::valueOf)
            .collect(Collectors.joining());
        return yyyyMmDd + memberId.toString() + randomNumbers;
    }
}
