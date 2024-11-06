package com.lovecloud.fundingmanagement.application;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationDisplayStatus;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.fundingmanagement.domain.repository.GuestFundingRepository;
import com.lovecloud.fundingmanagement.query.repository.OrderableFundingRepository;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponseMapper;
import com.lovecloud.fundingmanagement.query.response.FundingListResponse;
import com.lovecloud.fundingmanagement.query.response.FundingListResponseMapper;
import com.lovecloud.fundingmanagement.query.response.GuestFundingListResponse;
import com.lovecloud.fundingmanagement.query.response.GuestFundingListResponseMapper;
import com.lovecloud.fundingmanagement.query.response.OrderableFundingResponse;
import com.lovecloud.fundingmanagement.query.response.OrderableFundingResponseMapper;
import com.lovecloud.fundingmanagement.query.response.ParticipatedFundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.ParticipatedFundingListResponse;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FundingQueryService {

    private final FundingRepository fundingRepository;
    private final CoupleRepository coupleRepository;
    private final GuestFundingRepository guestFundingRepository;
    private final OrderableFundingRepository orderableFundingRepository;

    public List<FundingListResponse> findAllByCoupleId(Long coupleId) {
        coupleRepository.findByIdOrThrow(coupleId);
        List<Funding> fundings = fundingRepository.findByCoupleId(coupleId);
        if (fundings.isEmpty()) {
            return List.of();
        }
        return fundings.stream()
                .map(funding -> FundingListResponseMapper.map(funding,
                        guestFundingRepository.countByFundingIdAndParticipationStatus(
                                funding.getId(), ParticipationStatus.PAID)))
                .collect(Collectors.toList());
    }

    public FundingDetailResponse findById(Long fundingId) {
        Funding funding = fundingRepository.findByIdOrThrow(fundingId);
        int participantCount = guestFundingRepository.countByFundingIdAndParticipationStatus(
                fundingId, ParticipationStatus.PAID);
        return FundingDetailResponseMapper.map(
                funding, participantCount
        );
    }

    public List<GuestFundingListResponse> findAllGuestFundingsByFundingId(Long fundingId) {
        Funding funding = fundingRepository.findByIdOrThrow(fundingId);
        return guestFundingRepository.findByFundingIdAndParticipationStatus(fundingId,
                        ParticipationStatus.PAID).stream()
                .map(GuestFundingListResponseMapper::map)
                .collect(Collectors.toList());
    }

    public List<OrderableFundingResponse> findOrderableFundingsByUserId(Long userId) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        return orderableFundingRepository.findOrderableFundingsByCoupleId(couple.getId()).stream()
                .map(OrderableFundingResponseMapper::map)
                .toList();
    }

    public List<ParticipatedFundingListResponse> findParticipations(Long guestId) {
        return guestFundingRepository.findByGuestIdOrderByCreatedDateDesc(guestId).stream()
                .map(this::mapToParticipatedFundingListResponse)
                .toList();
    }

    public ParticipatedFundingDetailResponse findParticipation(Long guestId, Long participationId) {
        GuestFunding guestFunding = getGuestFundingForGuest(guestId, participationId);
        return mapToParticipatedFundingDetailResponse(guestFunding);
    }

    private ParticipatedFundingListResponse mapToParticipatedFundingListResponse(GuestFunding guestFunding) {
        Funding funding = guestFunding.getFunding();
        Payment payment = guestFunding.getPayment();
        ParticipationDisplayStatus displayStatus = ParticipationDisplayStatus.from(
                guestFunding.getParticipationStatus(), payment);
        return new ParticipatedFundingListResponse(
                guestFunding.getId(),
                guestFunding.getMerchantUid(),
                guestFunding.getName(),
                guestFunding.getPhoneNumber(),
                guestFunding.getEmail(),
                guestFunding.getFundingAmount(),
                guestFunding.getMessage(),
                funding.getId(),
                Optional.ofNullable(payment).map(Payment::getId).orElse(null),
                Optional.ofNullable(payment).map(Payment::getImpUid).orElse(null),
                Optional.ofNullable(payment).map(Payment::getPaidAt).orElse(null),
                Optional.ofNullable(payment).map(Payment::getPayMethod).orElse(null),
                displayStatus
        );
    }

    private GuestFunding getGuestFundingForGuest(Long guestId, Long participationId) {
        GuestFunding guestFunding = guestFundingRepository.findByIdOrThrow(participationId);
        if (!guestFunding.getGuest().getId().equals(guestId)) {
            throw new IllegalArgumentException("해당 하객의 참여 내역이 아닙니다.");
        }
        return guestFunding;
    }

    private ParticipatedFundingDetailResponse mapToParticipatedFundingDetailResponse(GuestFunding guestFunding) {
        Funding funding = guestFunding.getFunding();
        Payment payment = guestFunding.getPayment();
        ParticipationDisplayStatus displayStatus = ParticipationDisplayStatus.from(
                guestFunding.getParticipationStatus(), payment);
        return new ParticipatedFundingDetailResponse(
                guestFunding.getId(),
                guestFunding.getMerchantUid(),
                guestFunding.getName(),
                guestFunding.getPhoneNumber(),
                guestFunding.getEmail(),
                guestFunding.getFundingAmount(),
                guestFunding.getMessage(),
                funding.getId(),
                Optional.ofNullable(payment).map(Payment::getId).orElse(null),
                Optional.ofNullable(payment).map(Payment::getImpUid).orElse(null),
                Optional.ofNullable(payment).map(Payment::getPaidAt).orElse(null),
                Optional.ofNullable(payment).map(Payment::getPayMethod).orElse(null),
                displayStatus
        );
    }
}