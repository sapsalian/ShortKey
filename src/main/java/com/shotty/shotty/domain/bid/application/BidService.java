package com.shotty.shotty.domain.bid.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.balance.application.BalanceService;
import com.shotty.shotty.domain.bid.dao.BidRepository;
import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.domain.bid.dto.ShortsIdUploadDto;
import com.shotty.shotty.domain.payment.dao.PaymentRepository;
import com.shotty.shotty.domain.payment.domain.Payment;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BidService {
    private final BidRepository bidRepository;
    private final ApplyRepository applyRepository;
    private final PaymentRepository paymentRepository;
    private final BalanceService balanceService;

    public BidResponseDto create(BidRequestDto requestDto) {
        Long applyId = requestDto.applyId();
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NoSuchResourcException("존재하지 않는 지원 내역에 대한 입찰입니다."));

        Long postAuthorId = apply.getPost().getAuthor().getId();
        Long requesterId = requestDto.userId();

        if (!postAuthorId.equals(requesterId)) {
            throw new PermissionException("입찰은 공고 작성자만 가능합니다.");
        }

        if (bidRepository.existsByApplyId(applyId)) {
            throw new DataIntegrityViolationException("이미 입찰된 지원 내역입니다.");
        }

        Bid bid = new Bid(apply);
        bid = bidRepository.save(bid);

        return BidResponseDto.from(bid);
    }

    public boolean isBidded(Long applyId) {
        return bidRepository.existsByApplyId(applyId);
    }

    public void updateShortsId(ShortsIdUploadDto shortsIdUploadDto) {
        Bid bid = bidRepository.findByApplyId(shortsIdUploadDto.applyId())
                .orElseThrow(() -> new NoSuchResourcException("입찰되지 않은 지원내역입니다."));

        Long applierId = bid.getApply().getInfluencer().getUser().getId();

        if (!shortsIdUploadDto.requesterId().equals(applierId)) {
            throw new PermissionException("지원자 본인만 쇼츠 Id를 등록할 수 있습니다.");
        }

        bid.setShortsId(shortsIdUploadDto.shortsId());
        bidRepository.save(bid);
    }

    public void deleteByApplyId(Long applyId) {
        bidRepository.deleteByApplyId(applyId);
    }

    public void deleteByInfluencerId(Long influencerId) {
        paymentRepository.deleteAllByInfluencerId(influencerId);
        bidRepository.deleteAllByInfluencerId(influencerId);
    }

    @Transactional
    public void acceptBid(Long accepterId, Long applyId) {
        Bid bid = bidRepository.findByApplyId(applyId).orElseThrow(
                () -> new NoSuchResourcException("존재하지 않는 입찰내역입니다.")
        );

        if (bid.getShortsId() == null) {
            throw new NoSuchResourcException("아직 쇼츠 영상이 업로드 되지 않아 최종승인 할 수 없습니다.");
        }

        Long advertiserId = bid.getApply().getPost().getAuthor().getId();

        if (!accepterId.equals(advertiserId)) {
            throw new PermissionException("해당 입찰 내역에 대해 승인 권한이 없습니다.");
        }

        bid.accept();
        bidRepository.save(bid);

        Payment payment = paymentRepository.findById(bid.getId()).orElse(null);
        if (payment == null) {
            payment = Payment.create(bid);
            paymentRepository.save(payment);
        }
    }

}
