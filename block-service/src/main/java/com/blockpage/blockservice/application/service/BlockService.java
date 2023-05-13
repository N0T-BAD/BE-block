package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayRefundParams;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayApprovalResponse;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayRefundResponse;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort.BlockEntityDto;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort.PaymentEntityDto;
import com.blockpage.blockservice.application.port.out.PaymentRequestPort;
import com.blockpage.blockservice.application.port.out.PaymentCachingPort;
import com.blockpage.blockservice.domain.Block;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블럭 도메인 이벤트(C,U,D)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService implements BlockUseCase {

    private final BlockPersistencePort blockPersistencePort;
    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentRequestPort paymentRequestPort;
    private final PaymentCachingPort paymentCachingPort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<BlockEntityDto> blockEntityDtoList = blockPersistencePort.getMemberBlock(query.getMemberId());
        List<Block> blocks = blockEntityDtoList.stream()
            .map(Block::initBlockFromEntityDto)
            .collect(Collectors.toList());
        Integer totalBlocks = Block.getTotalBlock(blocks);
        return new BlockQueryDto(query.getMemberId(), totalBlocks);
    }

    @Override
    @Transactional
    public void createBlock(ChargeBlockQuery query) {
        Block block = Block.initBlockForCharge(query);
        blockPersistencePort.saveBlock(block);
    }

    @Override
    @Transactional
    public void consumeBlock(UpdateBlockQuery query) {
        List<BlockEntity> blockEntityList = blockPersistencePort.updateBlockQuantity(query.getMemberId());
        List<Block> blocks = blockEntityList.stream()
            .map(Block::initBlockFromEntity)
            .collect(Collectors.toList());
        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());
        consumeBlockQuantity(blockEntityList, consumeBlocks);
    }

    @Override
    @Transactional
    public KakaoPayReadyDto kakaoPayReady(KakaoReadyQuery query) {
        String orderNumber = createOrderNumber(query.getMemberId());
        KakaoPayReadyParams kakaoPayReadyParams = KakaoPayReadyParams.addEssentialParams(query, orderNumber);
        KakaoPayReadyDto response = paymentRequestPort.ready(kakaoPayReadyParams);
        paymentCachingPort.savePaymentReceipt(new PaymentReceiptDto(query, response, orderNumber));
        return response;
    }

    @Override
    @Transactional
    public KakaoPayApproveDto kakaoPayApprove(KakaoApproveQuery query) {
        PaymentReceiptDto receipt = paymentCachingPort.getPaymentReceiptByMemberId(query.getMemberId().toString());
        KakaoPayApprovalParams kakaoPayApprovalParams = KakaoPayApprovalParams.addEssentialParams(query, receipt);
        KakaoPayApproveDto response = paymentRequestPort.approval(kakaoPayApprovalParams);
        paymentPersistencePort.savePaymentRecord(new PaymentDto(receipt, response, "kakao"));
        return response;
    }

    @Override
    @Transactional
    public void refundBlock(refundBlockQuery query) {
        PaymentEntityDto paymentEntityDto = paymentPersistencePort.getPayment(query.getOrderId());
        BlockEntityDto orderedBlock = blockPersistencePort.getOrderedBlock(query.getOrderId());
        if (orderedBlock.getBlockQuantity().equals(paymentEntityDto.getBlockQuantity())) {
            if (query.getPaymentCompany().equals("kakao")) {
                KakaoPayRefundParams kakaoPayRefundParams = KakaoPayRefundParams.addEssentialParams(paymentEntityDto);
                KakaoPayRefundDto kakaoPayRefundDto = paymentRequestPort.cancel(kakaoPayRefundParams);
                blockPersistencePort.deleteBlockByOrderId(kakaoPayRefundDto.getOrderId());
            } else {
                throw new RuntimeException("잘못된 요청");
            }
        } else {
            throw new RuntimeException("환불 불가");
        }
    }

    //==서비스 Layer 편의 메서드==//
    public String createOrderNumber(Long memberId) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        String yyyyMmDd = LocalDate.now().toString().replace("-", "");
        String randomNumbers = Stream.generate(() -> random.nextInt(1, 10))
            .limit(6)
            .map(String::valueOf)
            .collect(Collectors.joining());
        return yyyyMmDd + memberId.toString() + randomNumbers;
    }

    private static void consumeBlockQuantity(List<BlockEntity> blockEntityList, List<Block> consumeBlocks) {
        consumeBlocks.stream().forEach(
            i -> blockEntityList.stream()
                .filter(e -> e.getId() == i.getBlockId())
                .findFirst()
                .get()
                .changeQuantity(i.getBlockQuantity())
        );
    }

    //==서비스 Layer DTO==//
    @Getter
    @AllArgsConstructor
    public class BlockQueryDto {

        private Long memberId;
        private Integer totalBlocks;
    }

    @Getter
    public static class KakaoPayReadyDto {

        private String tid;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private String created_at;

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
    @ToString
    @AllArgsConstructor
    public static class PaymentReceiptDto {

        private String memberId;
        private String tid;
        private String orderId;

        private String itemName;
        private String quantity;
        private String totalAmount;

        public PaymentReceiptDto(KakaoReadyQuery query, KakaoPayReadyDto dto, String orderNumber) {
            this.orderId = orderNumber;
            this.tid = dto.getTid();
            this.memberId = query.getMemberId().toString();
            this.itemName = query.getItemName();
            this.quantity = query.getQuantity().toString();
            this.totalAmount = query.getTotalAmount().toString();
        }
    }

    @Getter
    @AllArgsConstructor
    public class PaymentDto {

        private String memberId;
        private String tid;
        private String orderId;

        private String itemName;
        private String quantity;
        private String totalAmount;
        private String paymentCompany;

        private String paymentType;
        private LocalDateTime paymentTime;

        public PaymentDto(PaymentReceiptDto receipt, KakaoPayApproveDto approveDto, String paymentCompany) {
            this.tid = receipt.getTid();
            this.memberId = receipt.getMemberId();
            this.orderId = receipt.getOrderId();
            this.itemName = receipt.getItemName();
            this.quantity = receipt.getQuantity();
            this.totalAmount = receipt.getTotalAmount();
            this.paymentTime = approveDto.getApproved_at();
            this.paymentType = approveDto.getPayment_method_type();
            this.paymentCompany = paymentCompany;
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
}