package com.lovecloud.email.presentation;

import com.lovecloud.email.application.EmailSendService;
import com.lovecloud.email.application.EmailSetService;
import com.lovecloud.email.domain.EmailMessage;
import com.lovecloud.email.presentation.request.PasswordResetRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailSendService emailSendService;
    private final EmailSetService emailSetService;

    @PostMapping("/password-reset")
    public ResponseEntity<Long> sendTempPasswordEmail(@Valid @RequestBody PasswordResetRequest request) {

        EmailMessage emailMessage = emailSetService.setEmail(request.toCommand());
        emailSendService.sendEmail(emailMessage);
        return ResponseEntity.ok().build();
    }
}
