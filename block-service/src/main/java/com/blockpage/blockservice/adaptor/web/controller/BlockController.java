package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.web.view.ApiWrapperResponse;
import com.blockpage.blockservice.adaptor.web.requestbody.BlockRequest;
import com.blockpage.blockservice.adaptor.web.view.MemberBlockView;

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

    /**
     * Mock Data 작업중 (서비스 로직 없음)
     */
    @GetMapping
    public ResponseEntity<ApiWrapperResponse> getMemberBlocks() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiWrapperResponse(new MemberBlockView(1L, 5000)));
    }

    @PostMapping
    public ResponseEntity<ApiWrapperResponse> postBlocks(@RequestParam String type, @RequestBody BlockRequest blockRequest) {
        switch (type) {
            case "attendance": {
                //attendance 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : attendance = " + blockRequest.toString());
            }
            break;
            case "charge": {
                //charge 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : charge = " + blockRequest.toString());
            }
            break;

            case "refund": {
                //refund 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : refund = " + blockRequest.toString());
            }
            break;
            case "episode-bm": {
                //episode-bm 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : episode-bm = " + blockRequest.toString());
            }
            break;
            case "nft": {
                //nft 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : nft = " + blockRequest.toString());
            }
            break;
            case "profile-skin": {
                //nft 내역 생성 포트 + DTO 매핑 함수
                System.out.println("purchaseRequest : profile-skin = " + blockRequest.toString());
            }
            break;
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiWrapperResponse("리소스가 정상적으로 생성되었습니다."));
    }
}
