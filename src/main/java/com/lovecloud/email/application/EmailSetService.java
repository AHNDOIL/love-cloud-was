package com.lovecloud.email.application;

import com.lovecloud.auth.application.UserPasswordChangeService;
import com.lovecloud.email.application.command.PasswordResetCommand;
import com.lovecloud.email.domain.EmailMessage;
import com.lovecloud.global.util.UUIDUtil;
import com.lovecloud.usermanagement.application.UserQueryService;
import com.lovecloud.usermanagement.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSetService {

    private final UserQueryService userQueryService;
    private final UserPasswordChangeService userPasswordChangeService;

    public EmailMessage setEmail(final PasswordResetCommand passwordResetCommand){

        User user = userQueryService.getUserInfoByEmail(passwordResetCommand.email());

        String tempPassword = UUIDUtil.generateShortUUID();
        String email = passwordResetCommand.email();
        String name = passwordResetCommand.name();

        userPasswordChangeService.changePassword(user, tempPassword);

        return EmailMessage.builder()
                .toEmail(email)
                .subject("[LoveCloud] 임시 비밀번호 발송")
                .message("안녕하세요. " + name + "님! \n" +
                        "임시 비밀번호는 " + tempPassword + " 입니다. \n" +
                        "로그인 후 비밀번호를 변경해주세요.")
                .build();

    }
}
