package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.requestbody.BlockRequest;
import com.blockpage.blockservice.adaptor.web.view.BlockView;

import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.FindBlockQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase.UpdateBlockQuery;
import com.blockpage.blockservice.application.service.BlockService.BlockQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blocks")
public class BlockController {

    private final Long MEMBER_TEST_ID = 1L;

    private final BlockUseCase blockUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<BlockView>> getMemberBlocks() {
        BlockQueryDto blockQueryDto = blockUseCase.findAllBlock(new FindBlockQuery(MEMBER_TEST_ID));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new BlockView(blockQueryDto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BlockView>> postBlocks(
        @RequestParam String type,
        @RequestBody BlockRequest request
    ) {
        blockUseCase.createBlock(ChargeBlockQuery.toQuery(MEMBER_TEST_ID, type, request));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse(new BlockView("블럭이 성공적으로 등록되었습니다.")));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<BlockView>> patchBlocks(@RequestBody BlockRequest blockRequest) {
        blockUseCase.consumeBlock(UpdateBlockQuery.toQuery(MEMBER_TEST_ID, blockRequest));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new BlockView("블럭이 성공적으로 차감되었습니다.")));
    }
}