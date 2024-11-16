package com.lovecloud.invitationmanagement.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInvitationCommand(
        @NotNull Long userId,
        @NotNull Long invitationImageId,
        @NotBlank String weddingDateTime,
        @NotBlank String place,
        String content
) {
}
