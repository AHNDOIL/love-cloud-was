package com.lovecloud.fundingmanagement.application;

import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.fundingmanagement.application.validator.FundingValidator;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FundingCancellationService {

    private final WeddingUserRepository weddingUserRepository;
    private final FundingRepository fundingRepository;
    private final FundingValidator fundingValidator;
    private final CoupleRepository coupleRepository;
    private final WeddingCrowdFundingService weddingCrowdFundingService;

    public void cancelFunding(Long fundingId, Long userId) {
        WeddingUser user = weddingUserRepository.findByIdOrThrow(userId);
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        Funding funding = fundingRepository.findByIdOrThrow(fundingId);

        // 유효성 검사
        fundingValidator.validateFundingOwnership(funding, couple);
        fundingValidator.validateFundingStatus(funding, FundingStatus.IN_PROGRESS);

        // 블록체인 연동 - 펀딩 취소
        try {
            String transactionHash = weddingCrowdFundingService.cancelCrowdFunding(
                    funding.getBlockchainFundingId(),
                    couple.getWallet().getKeyfile()
            );

            log.info("블록체인 트랜잭션 해시: {}", transactionHash);
        } catch (Exception e) {
            throw new FundingBlockchainException("블록체인 연동 중 오류가 발생하였습니다.");
        }

        // 펀딩 상태 변경
        funding.cancel();
        fundingRepository.save(funding);
    }
}
