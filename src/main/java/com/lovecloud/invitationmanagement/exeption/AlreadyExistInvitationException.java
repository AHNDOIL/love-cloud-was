package com.lovecloud.invitationmanagement.exeption;

import com.lovecloud.global.exception.ErrorCode;
import com.lovecloud.global.exception.LoveCloudException;
import static org.springframework.http.HttpStatus.CONFLICT;


public class AlreadyExistInvitationException extends LoveCloudException {
    public AlreadyExistInvitationException() {
      super(new ErrorCode(CONFLICT, "이미 청첩장이 존재합니다."));
    }
}
