package com.lovecloud.email.presentation.request;

import com.lovecloud.email.application.command.PasswordResetCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordResetRequest(

        @NotBlank
        @Email(message = "email 형식이여야 합니다.")
        String email,

        @NotBlank
        @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한국어로 2~5자리여야 합니다.")
        String name
) {
        public PasswordResetCommand toCommand() {
                return new PasswordResetCommand(email, name);
        }
}
