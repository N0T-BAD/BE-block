package com.blockpage.blockservice.adaptor.web.controller;

import static com.blockpage.blockservice.application.port.in.BlockUseCase.*;

import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.requestbody.BlockRequest;
import com.blockpage.blockservice.adaptor.web.view.BlockView;

import com.blockpage.blockservice.adaptor.web.view.KakaoApproveView;
import com.blockpage.blockservice.adaptor.web.view.KakaoReadyView;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.FindBlockQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.KakaoApproveQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.UpdateBlockQuery;
import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayApproveDto;
import com.blockpage.blockservice.application.service.BlockService.KakaoPayReadyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blocks")
public class BlockController {

    private final long MEMBER_TEST_ID = 1L;
    private final String KAKAO_PAY = "kakaopay";
    private final String KAKAO_PAY_READY = "ready";
    private final String KAKAO_PAY_APPROVE = "approve";


    private final BlockUseCase blockUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<BlockView>> getMemberBlocks() {

        BlockQueryDto blockQueryDto = blockUseCase.findAllBlock(new FindBlockQuery(MEMBER_TEST_ID));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new BlockView(blockQueryDto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> postBlocks(
        @RequestParam String type,
        @RequestParam(required = false) String corp,
        @RequestParam(required = false) String step,
        @RequestBody BlockRequest request
    ) {
        if (corp == null) {
            blockUseCase.createBlock(new ChargeBlockQuery(MEMBER_TEST_ID, type, request.getQuantity()));
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("성공"));

        } else if (corp.equals(KAKAO_PAY) && step.equals(KAKAO_PAY_READY)) {
            KakaoPayReadyDto kakaoPayReadyDto = blockUseCase.kakaoPayReady(
                new KakaoReadyQuery(MEMBER_TEST_ID, request.getItemName(), request.getQuantity(),
                    request.getTotalAmount()));
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(new KakaoReadyView(kakaoPayReadyDto)));

        } else if (corp.equals(KAKAO_PAY) && step.equals(KAKAO_PAY_APPROVE)) {
            KakaoPayApproveDto kakaoPayReadyDto = blockUseCase.kakaoPayApprove(new KakaoApproveQuery(MEMBER_TEST_ID, request.getPgToken()));
            return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(new KakaoApproveView(kakaoPayReadyDto)));

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("실패"));
        }
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<String>> patchBlocks(@RequestBody BlockRequest blockRequest) {

        blockUseCase.updateBlock(new UpdateBlockQuery(MEMBER_TEST_ID, blockRequest.getQuantity()));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse("성공"));
    }
}