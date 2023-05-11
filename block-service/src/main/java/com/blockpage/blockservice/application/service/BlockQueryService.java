package com.blockpage.blockservice.application.service;

import com.blockpage.blockservice.application.port.in.BlockQuery;
import com.blockpage.blockservice.application.port.out.FindBlockPort;
import com.blockpage.blockservice.application.port.out.FindBlockPort.BlockEntityDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 블럭 조회(Read)전용 서비스, 도메인 영역을 접근하지 않음.
 */
@Service
@RequiredArgsConstructor
public class BlockQueryService implements BlockQuery {

    private final FindBlockPort findBlockPort;

    @Override
    public BlockQueryDto findBlock(FindQuery findQuery) {
        List<BlockEntityDto> memberBlocks = findBlockPort.getMemberBlock(findQuery);
        Integer totalBlocks = memberBlocks.stream()
            .filter(BlockEntityDto::getBlockValidate)
            .filter(BlockEntityDto::isExpired)
            .map(BlockEntityDto::getBlockQuantity)
            .reduce(Integer::sum)
            .get();

        return new BlockQueryDto(findQuery.getMemberId(), totalBlocks);
    }


    @Getter
    @AllArgsConstructor
    public class BlockQueryDto {

        private Long memberId;
        private Integer totalBlocks;
    }
}
