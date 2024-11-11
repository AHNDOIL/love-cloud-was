package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.invitationmanagement.application.InvitationQueryService;
import com.lovecloud.invitationmanagement.query.response.InvitationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
public class InvitationQueryController {

    private final InvitationQueryService invitationQueryService;

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationDetailResponse> detailInvitation(@PathVariable Long invitationId) {
        final InvitationDetailResponse invitation = invitationQueryService.findById(invitationId);
        return ResponseEntity.ok(invitation);
    }

    /**
     * 본인의 청첩장을 조회하는 API
     * */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<InvitationDetailResponse> myInvitation(@AuthenticationPrincipal SecurityUser securityUser) {
        final InvitationDetailResponse invitation = invitationQueryService.findByUserId(securityUser.user().getId());
        return ResponseEntity.ok(invitation);
    }
}
