package com.blockpage.blockservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //block

    //feign client
    BLOCK_SERVICE_SERVER_UNAVAILABLE("블럭 서비스가 이용이 불가능합니다.", HttpStatus.NO_CONTENT),         //오류 메세지 검토 필요
    BLOCK_SERVICE_CLIENT_BAD_REQUEST("블럭 서비스에 잘못된 요청을 보냈습니다.", HttpStatus.NO_CONTENT),     //오류 메세지 검토 필요

    MEMBER_SERVICE_SERVER_UNAVAILABLE("블럭 서비스가 이용이 불가능합니다.", HttpStatus.NO_CONTENT),         //오류 메세지 검토 필요
    MEMBER_SERVICE_CLIENT_BAD_REQUEST("블럭 서비스에 잘못된 요청을 보냈습니다.", HttpStatus.NO_CONTENT),     //오류 메세지 검토 필요

    UNKNOWN_ERROR("알수없는 에러가 발생하였습니다.", HttpStatus.NO_CONTENT),     //오류 메세지 검토 필요
    ;

    private final String message;
    private final HttpStatus httpStatus;

}
