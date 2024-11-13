package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.invitationmanagement.application.InvitationImageQueryService;
import com.lovecloud.invitationmanagement.query.response.InvitationImageListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/invitation-images")
@RequiredArgsConstructor
public class InvitationImageQueryController {
    private final InvitationImageQueryService invitationImageQueryService;

    //청첩장이미지 목록 조회
    @GetMapping
    public ResponseEntity<InvitationImageListResponse> getInvitationImages() {
        InvitationImageListResponse invitationImages = invitationImageQueryService.getInvitationImages();
        return ResponseEntity.ok(invitationImages);}
}
