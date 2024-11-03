package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingParticipationCancellationService;
import com.lovecloud.global.usermanager.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FundingParticipationCancellationController {

    private final FundingParticipationCancellationService fundingParticipationCancellationService;

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PatchMapping("/participations/{guestFundingId}/cancel")
    public ResponseEntity<Void> cancelParticipation(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long guestFundingId
    ) {
        final Long userId = securityUser.user().getId();
        fundingParticipationCancellationService.cancelParticipation(guestFundingId, userId);
        return ResponseEntity.ok().build();
    }
}
