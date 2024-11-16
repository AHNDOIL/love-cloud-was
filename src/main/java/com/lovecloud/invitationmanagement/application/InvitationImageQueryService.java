package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.query.response.InvitationImageListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvitationImageQueryService {
    private final InvitationImageRepository invitationImageRepository;

    public InvitationImageListResponse getInvitationImages() {
        List<InvitationImage> images = invitationImageRepository.findAll();
        return InvitationImageListResponse.from(images);
    }
}
