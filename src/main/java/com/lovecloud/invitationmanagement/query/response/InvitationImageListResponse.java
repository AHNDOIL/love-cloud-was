package com.lovecloud.invitationmanagement.query.response;

import com.lovecloud.invitationmanagement.domain.InvitationImage;

import java.util.List;

public record InvitationImageListResponse(
        List<InvitationImageDetailResponse> invitationImages
) {
    public static InvitationImageListResponse from(List<InvitationImage> images) {
        List<InvitationImageDetailResponse> invitationImages = images.stream()
                .map(InvitationImageDetailResponse::from)
                .toList();
        return new InvitationImageListResponse(invitationImages);
    }
}
