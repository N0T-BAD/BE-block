package com.blockpage.blockservice.application.port.out;

import com.blockpage.blockservice.application.port.out.FindBlockPort.BlockEntityDto;
import com.blockpage.blockservice.domain.Block;

public interface SaveBlockPort {

    BlockEntityDto saveBlock(Block block);
}
