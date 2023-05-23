package com.blockpage.blockservice.exception;

import static com.blockpage.blockservice.exception.ErrorCode.KAKAO_SERVICE_CLIENT_BAD_REQUEST;
import static com.blockpage.blockservice.exception.ErrorCode.KAKAO_SERVICE_SERVER_UNAVAILABLE;
import static com.blockpage.blockservice.exception.ErrorCode.UNKNOWN_ERROR;

import feign.Response;
import feign.codec.ErrorDecoder;

public class GlobalFeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (400 <= response.status() && response.status() < 500) {
            return new BusinessException(KAKAO_SERVICE_CLIENT_BAD_REQUEST.getMessage(), KAKAO_SERVICE_CLIENT_BAD_REQUEST.getHttpStatus());
        } else if (500 <= response.status() && response.status() < 600) {
            return new BusinessException(KAKAO_SERVICE_SERVER_UNAVAILABLE.getMessage(), KAKAO_SERVICE_SERVER_UNAVAILABLE.getHttpStatus());
        }
        return new BusinessException(UNKNOWN_ERROR.getMessage(), UNKNOWN_ERROR.getHttpStatus());
    }
}
