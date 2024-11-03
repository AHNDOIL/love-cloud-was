package com.lovecloud.fundingmanagement.application;

import com.lovecloud.auth.domain.GuestRepository;
import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.fundingmanagement.application.validator.FundingValidator;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.domain.repository.GuestFundingRepository;
import com.lovecloud.payment.application.PaymentService;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.PaymentStatus;
import com.lovecloud.payment.exception.PaymentCancellationFailedException;
import com.lovecloud.usermanagement.domain.Guest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FundingParticipationCancellationService {

    private final GuestFundingRepository guestFundingRepository;
    private final FundingValidator fundingValidator;
    private final WeddingCrowdFundingService weddingCrowdFundingService;
    private final GuestRepository guestRepository;
    private final PaymentService paymentService;

    public void cancelParticipation(Long guestFundingId, Long userId) {
        // 참여 내역 및 결제 정보 조회
        Guest guest = guestRepository.findByIdOrThrow(userId);
        GuestFunding guestFunding = guestFundingRepository.findByIdOrThrow(guestFundingId);
        Payment payment = guestFunding.getPayment();
        Funding funding = guestFunding.getFunding();

        // 유효성 검사
        fundingValidator.validateGuestFundingOwnership(guestFunding, guest);
        fundingValidator.validateGuestFundingStatus(guestFunding, ParticipationStatus.PAID);
        fundingValidator.validatePaymentStatus(payment, PaymentStatus.PAID);
        fundingValidator.validateFundingStatus(funding, FundingStatus.IN_PROGRESS);

        // 결제 취소
        try {
            paymentService.cancelPayment(payment.getImpUid());
        } catch (Exception e) {
            throw new PaymentCancellationFailedException();
        }

        // 블록체인 연동 - 참여 취소
        try {
            String transactionHash = weddingCrowdFundingService.cancelContribution(
                    funding.getBlockchainFundingId(),
                    guest.getWallet().getKeyfile()
            );

            log.info("블록체인 트랜잭션 해시: {}", transactionHash);
        } catch (Exception e) {
            throw new FundingBlockchainException("블록체인 연동 중 오류가 발생하였습니다.");
        }

        // GuestFunding 및 Funding 상태 업데이트
        guestFunding.updateStatus(ParticipationStatus.CANCELLED);
        funding.decresaseCurrentAmount(guestFunding.getFundingAmount());
        guestFundingRepository.save(guestFunding);
    }
}
