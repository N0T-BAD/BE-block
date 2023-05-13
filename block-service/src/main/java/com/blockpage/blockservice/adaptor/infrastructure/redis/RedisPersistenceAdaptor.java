package com.blockpage.blockservice.adaptor.infrastructure.redis;

import com.blockpage.blockservice.application.port.out.PaymentCachingPort;
import com.blockpage.blockservice.application.service.BlockService.PaymentReceiptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPersistenceAdaptor implements PaymentCachingPort {

    private final RedisRepository redisRepository;

    @Override
    public void savePaymentReceipt(PaymentReceiptDto paymentReceiptDto) {
        redisRepository.saveHashReceipt(paymentReceiptDto);
    }

    @Override
    public PaymentReceiptDto getPaymentReceiptByMemberId(String memberId) {
        return redisRepository.getHashReceiptByMemberId(memberId);
    }
}
