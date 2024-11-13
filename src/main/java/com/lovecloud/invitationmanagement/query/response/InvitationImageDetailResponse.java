package com.lovecloud.invitationmanagement.query.response;

import com.lovecloud.invitationmanagement.domain.InvitationImage;

public record InvitationImageDetailResponse(
        Long id,
        String imageName
) {
    public static InvitationImageDetailResponse from(InvitationImage image) {
        return new InvitationImageDetailResponse(image.getId(), image.getImageName());
    }
}
