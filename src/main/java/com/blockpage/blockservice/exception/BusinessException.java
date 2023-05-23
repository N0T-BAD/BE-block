package com.blockpage.blockservice.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public BusinessException(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
