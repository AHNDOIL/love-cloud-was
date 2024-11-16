package com.lovecloud.usermanagement.exception;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidUserTypeException extends LoveCloudException {

    public InvalidUserTypeException() {
        super(new ErrorCode(BAD_REQUEST, "초대 코드는 WeddingUser에게만 존재합니다."));
    }
}