package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayApprovalParams;
import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayApprovalResponse;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.adaptor.infrastructure.mysql.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort;
import com.blockpage.blockservice.application.port.out.BlockPersistencePort.BlockEntityDto;
import com.blockpage.blockservice.application.port.out.PaymentPersistencePort;
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
 * 블럭 도메인 이벤트(C,U,D) 서비스
 * 1. 블럭 조회
 * 2. 블럭 생성 (현금충전, 출석, 게임)
 * 3. 블럭 수정 (소비)
 * 4. 블럭 생성 (현금충전 전 결제(준비,승인))
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
        paymentPersistencePort.savePaymentRecord(new PaymentDto(receipt, response, "KAKAO"));
        return response;
    }

    @Override
    @Transactional
    public void refundBlock(refundBlockQuery refundBlockQuery) {

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
}