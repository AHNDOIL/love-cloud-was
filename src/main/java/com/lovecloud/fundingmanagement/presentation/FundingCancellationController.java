package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingCancellationService;
import com.lovecloud.global.usermanager.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FundingCancellationController {

    private final FundingCancellationService fundingCancellationService;

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @PatchMapping("/fundings/{fundingId}/cancel")
    public ResponseEntity<Void> cancelFunding(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long fundingId
    ) {
        final Long userId = securityUser.user().getId();
        fundingCancellationService.cancelFunding(fundingId, userId);
        return ResponseEntity.ok().build();
    }
}
