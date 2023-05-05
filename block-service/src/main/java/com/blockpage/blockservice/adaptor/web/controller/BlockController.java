package com.blockpage.blockservice.adaptor.web.controller;

import com.blockpage.blockservice.adaptor.infrastructure.entity.BlockEntity;
import com.blockpage.blockservice.adaptor.infrastructure.repository.BlockRepository;

import com.blockpage.blockservice.adaptor.web.view.MemberBlockView;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mock/v1/blocks")
public class BlockController {

    private final BlockRepository blockRepository;

    /**
     * 해당 유저의 블럭 조회 API
     */
    @GetMapping
    public ResponseEntity getMemberBlocks() {
        Long memberId = 1L;
        List<BlockEntity> blocks = blockRepository.findByMemberId(memberId);
        Optional<Integer> totalBlocks = (Optional<Integer>) blocks.stream()
            .filter(BlockEntity::getBlockValidate)
            .map(BlockEntity::getBlockQuantity)
            .reduce(Integer::sum);
        if (totalBlocks.isPresent()) {
            MemberBlockView result = MemberBlockView.toViewFromMemberBlocks(memberId, totalBlocks.get());
            return ResponseEntity.status(HttpStatus.OK)
                .body(result);
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body("DB에 값이 없습니다.");
    }
}
