package com.shotty.shotty.domain.bid.application;

import com.shotty.shotty.domain.apply.dao.ApplyRepository;
import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.bid.dao.BidRepository;
import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import com.shotty.shotty.global.common.exception.custom_exception.PermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final ApplyRepository applyRepository;

    public BidResponseDto create(BidRequestDto requestDto) {
        Long applyId = requestDto.applyId();
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NoSuchResourcException("존재하지 않는 지원 내역에 대한 입찰입니다."));

        Long postAuthorId = apply.getPost().getAuthor().getId();
        Long requesterId = requestDto.userId();

        if (!postAuthorId.equals(requesterId)) {
            throw new PermissionException("입찰은 공고 작성자만 가능합니다.");
        }

        Bid bid = bidRepository.save(new Bid(apply));

        return BidResponseDto.from(bid);
    }
}
