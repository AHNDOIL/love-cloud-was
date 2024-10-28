package com.lovecloud.email.application;

import com.lovecloud.email.application.command.PasswordResetCommand;
import com.lovecloud.email.domain.EmailMessage;
import com.lovecloud.usermanagement.application.UserQueryService;
import com.lovecloud.usermanagement.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSetService {

    private final UserQueryService userQueryService;

    public EmailMessage setEmail(final PasswordResetCommand passwordResetCommand){

        User user = userQueryService.getUserInfoByEmail(passwordResetCommand.email());

        String tempPassword = "1234"; // 임시 비밀번호 "1234"
        String email = passwordResetCommand.email();
        String name = passwordResetCommand.name();

        //TODO: 임시 비밀번호로 변경 로직 추가

        return EmailMessage.builder()
                .toEmail(email)
                .subject("[LoveCloud] 임시 비밀번호")
                .message("안녕하세요. " + name + "님! \n" +
                        "임시 비밀번호는 " + tempPassword + " 입니다. \n" +
                        "로그인 후 비밀번호를 변경해주세요.")
                .build();

    }
}
