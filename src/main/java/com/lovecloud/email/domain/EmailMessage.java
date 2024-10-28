package com.lovecloud.email.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailMessage {

    private final String toEmail;
    private final String subject;
    private final String message;
}
