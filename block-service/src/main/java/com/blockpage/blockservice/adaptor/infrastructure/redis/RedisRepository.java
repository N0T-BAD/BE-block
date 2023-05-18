package com.blockpage.blockservice.adaptor.infrastructure.redis;

import com.blockpage.blockservice.application.service.PaymentService.PaymentReceiptDto;
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

    public void saveHashReceipt(PaymentReceiptDto paymentReceiptDto) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(paymentReceiptDto.getMemberId(), TID, paymentReceiptDto.getTid());
        hashOperations.put(paymentReceiptDto.getMemberId(), ORDER_ID, paymentReceiptDto.getOrderId());
        hashOperations.put(paymentReceiptDto.getMemberId(), ITEM_NAME, paymentReceiptDto.getItemName());
        hashOperations.put(paymentReceiptDto.getMemberId(), QUANTITY, paymentReceiptDto.getQuantity());
        hashOperations.put(paymentReceiptDto.getMemberId(), TOTAL_AMOUNT, paymentReceiptDto.getTotalAmount());
    }

    public PaymentReceiptDto getHashReceiptByMemberId(String memberId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        return new PaymentReceiptDto(
            memberId,
            hashOperations.get(memberId, TID),
            hashOperations.get(memberId, ORDER_ID),
            hashOperations.get(memberId, ITEM_NAME),
            hashOperations.get(memberId, QUANTITY),
            hashOperations.get(memberId, TOTAL_AMOUNT)
        );
    }
}
