package com.lovecloud.email.presentation.request;

import com.lovecloud.email.application.command.PasswordChangeCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordChangeRequest(
        @NotBlank
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 길이, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
        String password
) {

        public PasswordChangeCommand toCommand() {
                return new PasswordChangeCommand(password);
        }
}
