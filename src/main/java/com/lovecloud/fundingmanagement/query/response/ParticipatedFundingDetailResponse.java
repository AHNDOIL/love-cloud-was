package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.ParticipationDisplayStatus;
import java.time.LocalDateTime;

public record ParticipatedFundingDetailResponse(
        Long guestFundingId,
        String merchantUid,
        String name,
        String phoneNumber,
        String email,
        Long amount,
        String message,
        Long fundingId,
        Long paymentId,
        String impUid,
        LocalDateTime paidAt,
        String payMethod,
        ParticipationDisplayStatus status
) {
}
