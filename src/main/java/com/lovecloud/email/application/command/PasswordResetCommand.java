package com.lovecloud.email.application.command;

public record PasswordResetCommand(
        String email,
        String name
) {
}
