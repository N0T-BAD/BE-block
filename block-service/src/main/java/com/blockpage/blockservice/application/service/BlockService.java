package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.adaptor.external.kakao.requestbody.KakaoPayReadyParams;
import com.blockpage.blockservice.adaptor.external.kakao.response.KakaoPayReadyResponse;
import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.out.BlockPort;
import com.blockpage.blockservice.application.port.out.BlockPort.BlockEntityDto;
import com.blockpage.blockservice.application.port.out.PaymentPort;
import com.blockpage.blockservice.domain.Block;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 블럭 도메인 이벤트(C,U,D) 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService implements BlockUseCase {

    private final BlockPort blockPort;
    private final PaymentPort paymentPort;

    @Override
    public BlockQueryDto findAllBlock(FindBlockQuery query) {
        List<BlockEntityDto> blockEntityDtoList = blockPort.getMemberBlock(query.getMemberId());
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
        blockPort.saveBlock(block);
    }

    @Override
    @Transactional
    public void updateBlock(UpdateBlockQuery query) {
        List<BlockEntity> blockEntityList = blockPort.updateBlockQuantity(query.getMemberId());
        List<Block> blocks = blockEntityList.stream()
            .map(Block::initBlockFromEntity)
            .collect(Collectors.toList());

        List<Block> consumeBlocks = Block.comsumeBlockList(blocks, query.getBlockQuantity());

        consumeBlocks.stream().forEach(
            i -> blockEntityList.stream()
                .filter(e -> e.getId() == i.getBlockId())
                .findFirst()
                .get()
                .changeQuantity(i.getBlockQuantity())
        );
    }

    @Override
    public KakaoPayReadyDto kakaoPayReady(KakaoReadyQuery kakaoReadyQuery) {
        KakaoPayReadyParams kakaoPayReadyParams = KakaoPayReadyParams.addEssentialParams(kakaoReadyQuery, "1");
        KakaoPayReadyDto response = paymentPort.ready(kakaoPayReadyParams);

        return response;
    }

    @Override
    public KakaoPayApproveDto kakaoPayApprove(KakaoApproveQuery kakaoApproveQuery) {
        return null;
    }

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

        private String aid;
        private String tid;
        private String cid;
        private String sid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Amount amount;
        private String item_name;
        private String item_code;
        private int quantity;
        private String created_at;
        private String approved_at;
        private String payload;
    }

    @Getter
    @AllArgsConstructor
    private class Amount {

        private int total; // 총  금액
        private int tax_free; // 비과세 금액
        private int tax; // 부가세 금액
        private int point; // 사용한 포인트
        private int discount; // 할인금액
        private int green_deposit; // 컵 보증금
    }
}
