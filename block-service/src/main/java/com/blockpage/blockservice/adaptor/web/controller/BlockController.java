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

    private long MEMBER_TEST_ID = 1L;

    private final BlockUseCase blockUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<BlockView>> getMemberBlocks() {

        BlockQueryDto blockQueryDto = blockUseCase.findAllBlock(new FindBlockQuery(MEMBER_TEST_ID));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new BlockView(blockQueryDto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> postBlocks(@RequestParam String type, @RequestBody BlockRequest blockRequest) {
        blockUseCase.createBlock(new ChargeBlockQuery(MEMBER_TEST_ID, type, blockRequest.getBlockQuantity()));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse("블럭이 생성되었습니다."));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<String>> patchBlocks(@RequestBody BlockRequest blockRequest) {
        blockUseCase.updateBlock(new UpdateBlockQuery(MEMBER_TEST_ID, blockRequest.getBlockQuantity()));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse("블럭이 생성되었습니다."));
    }
}