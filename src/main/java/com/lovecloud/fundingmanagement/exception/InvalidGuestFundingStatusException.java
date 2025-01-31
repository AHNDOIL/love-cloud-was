package com.lovecloud.fundingmanagement.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;

public class InvalidGuestFundingStatusException extends LoveCloudException {

    public InvalidGuestFundingStatusException() {
        super(new ErrorCode(BAD_REQUEST, "참여 상태가 유효하지 않습니다."));
    }
}
