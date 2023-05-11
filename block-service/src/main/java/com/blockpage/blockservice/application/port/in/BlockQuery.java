package com.blockpage.blockservice.application.port.in;

import com.blockpage.blockservice.application.service.BlockQueryService.BlockQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface BlockQuery {

    BlockQueryDto findBlock(FindQuery findQuery);

    @Getter
    @AllArgsConstructor
    class FindQuery {
        private Long memberId;
    }
}
