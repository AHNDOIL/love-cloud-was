package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import com.lovecloud.invitationmanagement.application.command.UpdateInvitationCommand;
import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.invitationmanagement.exeption.NotFoundInvitationException;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationCreateService {
    private final InvitationRepository invitationRepository;
    private final InvitationImageRepository invitationImageRepository;
    private final CoupleRepository coupleRepository;

    public Long addInvitation(final CreateInvitationCommand command) {

        InvitationImage invitationImage = invitationImageRepository.findByIdOrThrow(command.invitationImageId());
        Invitation invitation = command.toInvitation(invitationImage);

        Invitation savedInvitation = invitationRepository.save(invitation);

        return savedInvitation.getId();
    }

    public Long updateInvitation(UpdateInvitationCommand command) {
        InvitationImage invitationImage = invitationImageRepository.findByIdOrThrow(command.invitationImageId());
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        Invitation invitation = getInvitationByCoupleOrThrow(couple);

        invitation.update(command.place(), LocalDateTime.parse(command.weddingDateTime()), command.content(), invitationImage);

        return invitation.getId();
    }

    public void deleteInvitation(Long userId) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        Invitation invitation = getInvitationByCoupleOrThrow(couple);
        couple.deleteInvitation();
        invitationRepository.delete(invitation);
    }

    private Invitation getInvitationByCoupleOrThrow(Couple couple){
        return Optional.ofNullable(couple.getInvitation())
                .orElseThrow(NotFoundInvitationException::new);
    }
}
