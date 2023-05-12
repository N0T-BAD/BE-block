package com.blockpage.blockservice.adaptor.infrastructure.redis;

import com.blockpage.blockservice.application.port.out.PaymentCachingPort;
import com.blockpage.blockservice.application.service.BlockService.PaymentOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPersistenceAdaptor implements PaymentCachingPort {

    private final RedisRepository redisRepository;

    @Override
    public void savePaymentReceipt(PaymentOutDto paymentOutDto) {
        redisRepository.saveHashReceipt(paymentOutDto);
    }

    @Override
    public PaymentOutDto getPaymentReceiptByMemberId(String memberId) {
        return redisRepository.getHashReceiptByMemberId(memberId);
    }
}
