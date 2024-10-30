package com.lovecloud.fundingmanagement.application.validator;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.exception.FundingTargetExceededException;
import com.lovecloud.fundingmanagement.exception.InvalidFundingStatusException;
import com.lovecloud.fundingmanagement.exception.InvalidGuestFundingStatusException;
import com.lovecloud.fundingmanagement.exception.MismatchedAmountsException;
import com.lovecloud.fundingmanagement.exception.MismatchedMerchantUidsException;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.PaymentStatus;
import com.lovecloud.payment.exception.InvalidPaymentStatusException;
import org.springframework.stereotype.Component;

@Component
public class FundingValidator {

    public void validateFundingStatus(Funding funding, FundingStatus status) {
        if (funding.getStatus() != status) {
            throw new InvalidFundingStatusException();
        }
    }

    public void validatePaymentStatus(Payment payment, PaymentStatus status) {
        if (payment.getPaymentStatus() != status) {
            throw new InvalidPaymentStatusException();
        }
    }

    public void validateGuestFundingStatus(GuestFunding guestFunding, ParticipationStatus status) {
        if (guestFunding.getParticipationStatus() != status) {
            throw new InvalidGuestFundingStatusException();
        }
    }

    public void validateTargetAmountNotExceeded(Funding funding, Long amount) {
        if (funding.getCurrentAmount() + amount > funding.getTargetAmount()) {
            throw new FundingTargetExceededException();
        }
    }

    public void validateMatchingMerchantUids(String guestFundingMerchantUid,
            String paymentMerchantUid) {
        if (!guestFundingMerchantUid.equals(paymentMerchantUid)) {
            throw new MismatchedMerchantUidsException();
        }
    }

    public void validateMatchingAmounts(Long guestFundingAmount, Long paymentAmount) {
        if (!guestFundingAmount.equals(paymentAmount)) {
            throw new MismatchedAmountsException();
        }
    }
}
