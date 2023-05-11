package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.web.view.ApiResponse;
import com.blockpage.blockservice.adaptor.web.requestbody.BlockRequest;
import com.blockpage.blockservice.adaptor.web.view.MemberBlockView;

import com.blockpage.blockservice.application.port.in.BlockQuery;
import com.blockpage.blockservice.application.port.in.BlockQuery.FindQuery;
import com.blockpage.blockservice.application.port.in.BlockUseCase;
import com.blockpage.blockservice.application.port.in.BlockUseCase.ChargeBlockQuery;
import com.blockpage.blockservice.application.service.BlockQueryService.BlockQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final BlockQuery blockQuery;
    private final BlockUseCase blockUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<MemberBlockView>> getMemberBlocks() {

        BlockQueryDto blockQueryDto = blockQuery.findBlock(new FindQuery(MEMBER_TEST_ID));
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponse(new MemberBlockView(blockQueryDto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> postBlocks(@RequestParam String type, @RequestBody BlockRequest blockRequest) {
        blockUseCase.createBlock(new ChargeBlockQuery(MEMBER_TEST_ID, type, blockRequest.getBlockQuantity()));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse("블럭이 생성되었습니다."));
    }
}

// switch (type) {
//     case "attendance": {
//     //attendance 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : attendance = " + blockRequest.toString());
//     }
//     break;
//     case "charge": {
//     //charge 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : charge = " + blockRequest.toString());
//     }
//     break;
//
//     case "refund": {
//     //refund 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : refund = " + blockRequest.toString());
//     }
//     break;
//     case "episode-bm": {
//     //episode-bm 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : episode-bm = " + blockRequest.toString());
//     }
//     break;
//     case "nft": {
//     //nft 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : nft = " + blockRequest.toString());
//     }
//     break;
//     case "profile-skin": {
//     //nft 내역 생성 포트 + DTO 매핑 함수
//     System.out.println("purchaseRequest : profile-skin = " + blockRequest.toString());
//     }
//     break;
//     }
