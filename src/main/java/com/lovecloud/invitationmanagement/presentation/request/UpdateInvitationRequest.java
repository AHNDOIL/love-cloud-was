package com.lovecloud.invitationmanagement.presentation.request;

import com.lovecloud.invitationmanagement.application.command.UpdateInvitationCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInvitationRequest(
        @NotNull Long invitationImageId,
        @NotBlank String weddingDateTime,
        @NotBlank String place,
        String content
) {
    public UpdateInvitationCommand toCommand(Long id, Long invitationId) {
        return new UpdateInvitationCommand(id, invitationId, this.invitationImageId, this.weddingDateTime, this.place, this.content);
    }
}
