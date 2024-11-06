package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.PaymentStatus;

public enum ParticipationDisplayStatus {
    PENDING("결제 대기 중"),
    COMPLETED("참여 완료"),
    FAILED("참여 실패"),
    CANCELLED("참여 취소"),
    UNKNOWN("알 수 없음");

    private final String message;

    ParticipationDisplayStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ParticipationDisplayStatus from(ParticipationStatus participationStatus, Payment payment) {
        if (participationStatus == ParticipationStatus.PENDING
                && (payment == null || payment.getPaymentStatus() == PaymentStatus.READY)) {
            return PENDING;
        }
        if (participationStatus == ParticipationStatus.PAID
                && payment != null && payment.getPaymentStatus() == PaymentStatus.PAID) {
            return COMPLETED;
        }
        if (participationStatus == ParticipationStatus.FAILED
                && payment != null && payment.getPaymentStatus() == PaymentStatus.FAILED) {
            return FAILED;
        }
        if (participationStatus == ParticipationStatus.CANCELLED
                && payment != null && payment.getPaymentStatus() == PaymentStatus.CANCELED) {
            return CANCELLED;
        }
        return UNKNOWN;
    }
}
