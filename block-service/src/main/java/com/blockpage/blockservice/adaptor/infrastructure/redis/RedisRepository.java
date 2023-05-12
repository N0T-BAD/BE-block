package com.blockpage.blockservice.adaptor.infrastructure.redis;

import com.blockpage.blockservice.application.service.BlockService.PaymentOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate redisTemplate;

    private final String TID = "tid";
    private final String ORDER_ID = "orderId";
    private final String ITEM_NAME = "itemName";
    private final String QUANTITY = "quantity";
    private final String TOTAL_AMOUNT = "totalAmount";

    public void saveHashReceipt(PaymentOutDto paymentOutDto) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(paymentOutDto.getMemberId(), TID, paymentOutDto.getTid());
        hashOperations.put(paymentOutDto.getMemberId(), ORDER_ID, paymentOutDto.getOrderId());
        hashOperations.put(paymentOutDto.getMemberId(), ITEM_NAME, paymentOutDto.getItemName());
        hashOperations.put(paymentOutDto.getMemberId(), QUANTITY, paymentOutDto.getQuantity());
        hashOperations.put(paymentOutDto.getMemberId(), TOTAL_AMOUNT, paymentOutDto.getTotalAmount());
    }

    public PaymentOutDto getHashReceiptByMemberId(String memberId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        return new PaymentOutDto(
            memberId,
            hashOperations.get(memberId, TID),
            hashOperations.get(memberId, ORDER_ID),
            hashOperations.get(memberId, ITEM_NAME),
            hashOperations.get(memberId, QUANTITY),
            hashOperations.get(memberId, TOTAL_AMOUNT)
        );
    }
}
