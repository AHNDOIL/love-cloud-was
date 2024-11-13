package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.invitationmanagement.application.InvitationService;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.presentation.request.UpdateInvitationRequest;
import com.lovecloud.usermanagement.application.CoupleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/invitations")
@RestController
public class InvitationController {

    private final InvitationService invitationService;
    private final CoupleService coupleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> invitationAdd(@Valid @RequestBody CreateInvitationRequest request,
                                              @AuthenticationPrincipal SecurityUser securityUser)
    {

        final Long invitationId = invitationService.addInvitation(request.toCommand(securityUser.user().getId() ));
        coupleService.updateCoupleInvitation(securityUser.user().getId(), invitationId);

        return ResponseEntity.ok(invitationId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> invitationUpdate(@Valid @RequestBody UpdateInvitationRequest request,
                                                 @AuthenticationPrincipal SecurityUser securityUser)
    {
        final Long invitaionId = invitationService.updateInvitation(request.toCommand(securityUser.user().getId()));
        return ResponseEntity.created(URI.create("invitations/"+ invitaionId)).build();
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Void> invitationDelete(@AuthenticationPrincipal SecurityUser securityUser)
    {
        invitationService.deleteInvitation(securityUser.user().getId());
        return ResponseEntity.noContent().build();
    }

}
