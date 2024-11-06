package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingQueryService;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.FundingListResponse;
import com.lovecloud.fundingmanagement.query.response.GuestFundingListResponse;
import com.lovecloud.fundingmanagement.query.response.OrderableFundingResponse;
import com.lovecloud.fundingmanagement.query.response.ParticipatedFundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.ParticipatedFundingListResponse;
import com.lovecloud.global.usermanager.SecurityUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FundingQueryController {

    private final FundingQueryService fundingQueryService;

    @GetMapping("/couples/{coupleId}/fundings")
    public ResponseEntity<List<FundingListResponse>> listFundings(
            @PathVariable Long coupleId
    ) {
        final List<FundingListResponse> fundings = fundingQueryService.findAllByCoupleId(coupleId);
        return ResponseEntity.ok(fundings);
    }

    @GetMapping("/fundings/{fundingId}")
    public ResponseEntity<FundingDetailResponse> detailFunding(
            @PathVariable Long fundingId
    ) {
        final FundingDetailResponse funding = fundingQueryService.findById(fundingId);
        return ResponseEntity.ok(funding);
    }

    @GetMapping("/fundings/{fundingId}/guest-fundings")
    public ResponseEntity<List<GuestFundingListResponse>> listGuestFundings(
            @PathVariable Long fundingId
    ) {
        final List<GuestFundingListResponse> guestFundings =
                fundingQueryService.findAllGuestFundingsByFundingId(fundingId);
        return ResponseEntity.ok(guestFundings);
    }

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @GetMapping("/orderable-fundings")
    public ResponseEntity<List<OrderableFundingResponse>> listOrderableFundings(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        final Long userId = securityUser.user().getId();
        final List<OrderableFundingResponse> orderableFundings = fundingQueryService.findOrderableFundingsByUserId(
                userId);
        return ResponseEntity.ok(orderableFundings);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/participations")
    public ResponseEntity<List<ParticipatedFundingListResponse>> listParticipatedFundings(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        final Long guestId = securityUser.user().getId();
        final List<ParticipatedFundingListResponse> participations = fundingQueryService.findParticipations(guestId);
        return ResponseEntity.ok(participations);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/participations/{participationId}")
    public ResponseEntity<ParticipatedFundingDetailResponse> detailParticipatedFunding(
            @PathVariable Long participationId,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        final Long guestId = securityUser.user().getId();
        final ParticipatedFundingDetailResponse participation = fundingQueryService.findParticipation(guestId, participationId);
        return ResponseEntity.ok(participation);
    }
}
