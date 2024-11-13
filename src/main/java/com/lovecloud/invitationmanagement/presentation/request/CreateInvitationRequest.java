package com.lovecloud.invitationmanagement.presentation.request;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInvitationRequest(
    @NotNull Long invitationImageId,
    @NotBlank String weddingDateTime,
    @NotBlank String place,
    String content

) {


    public CreateInvitationCommand toCommand(Long userId) {
        return new CreateInvitationCommand(userId, nvitationImageId, weddingDateTime, place, content);
    }
}

