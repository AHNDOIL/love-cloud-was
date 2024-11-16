package com.lovecloud.email.application;

import com.lovecloud.email.domain.EmailMessage;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private JavaMailSender javaMailSender;

    public void sendEmail(final EmailMessage emailMessage){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(emailMessage.getToEmail());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getMessage());

            javaMailSender.send(mimeMessage);
        } catch (Exception e){ //TODO: 예외 처리를 위한 코드 추가
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
