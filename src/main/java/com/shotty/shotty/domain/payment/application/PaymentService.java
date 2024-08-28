package com.shotty.shotty.domain.payment.application;

import com.shotty.shotty.domain.balance.application.BalanceService;
import com.shotty.shotty.domain.balance.dao.BalanceRepository;
import com.shotty.shotty.domain.balance.exception.custom_exception.BalanceNotFoundException;
import com.shotty.shotty.domain.balance.exception.custom_exception.NotEnoughBalanceException;
import com.shotty.shotty.domain.user.dao.UserRepository;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.youtube.dto.video.ShortsSimpleInfoDto;
import com.shotty.shotty.YoutubeService;
import com.shotty.shotty.domain.bid.dao.BidRepository;
import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.payment.dao.PaymentRepository;
import com.shotty.shotty.domain.payment.domain.Payment;
import com.shotty.shotty.youtube.dto.video.YouTubeVideoItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class PaymentService {
    private final BidRepository bidrepository;
    private final YoutubeService youtubeService;
    private final PaymentRepository paymentRepository;

    private static final float PRICE_PER_VIEW = 0.1f;
    private static final int PAYMENT_CYCLE = 300000; //5분
    private final BalanceService balanceService;

    @Scheduled(fixedRate = PAYMENT_CYCLE)
    //@Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void doPayment() {
        List<Bid> acceptedBids = bidrepository.findAcceptedBidsWithJoinFetch();
        List<String> shortsIds = new ArrayList<>();
        //최종 승인된 입찰 전체의 쇼츠 정보 가져오기
        acceptedBids.forEach(bid -> {
            shortsIds.add(bid.getShortsId());
        });

        ShortsSimpleInfoDto shortsSimpleInfoDto = youtubeService.searchVideos(shortsIds);

        HashMap<String, YouTubeVideoItem> videoItemMap = new HashMap<>();
        shortsSimpleInfoDto.getItems().forEach(
                youTubeVideoItem -> {
                    videoItemMap.put(youTubeVideoItem.getId(), youTubeVideoItem);
                }
        );

        System.out.println("videoItemMap = " + videoItemMap);

        //db에서 정산 정보 조회
        List<Payment> payments = paymentRepository.findAll();

        HashMap<Long, Payment> paymentMap = new HashMap<>();
        payments.forEach(payment -> {
            paymentMap.put(payment.getBid().getId(), payment);
        });
        System.out.println("paymentMap = " + paymentMap);

        //정산 로직
        for (Bid bid : acceptedBids) {
            payments(bid, paymentMap, videoItemMap);

        }
    }

    @Transactional
    public void payments(Bid bid, HashMap<Long, Payment> paymentMap, HashMap<String, YouTubeVideoItem> videoItemMap) {
        Payment payment = paymentMap.get(bid.getId());
        YouTubeVideoItem youTubeVideoItem = videoItemMap.get(bid.getShortsId());
        int curViewCount = Integer.parseInt(youTubeVideoItem.getStatistics().getViewCount());
        int increment = curViewCount - payment.getLastViewCount();
        int amount = (int)(increment * PRICE_PER_VIEW);

        User influencer = bid.getApply().getInfluencer().getUser();
        Long user_id = influencer.getId();
        User author = bid.getApply().getPost().getAuthor();
        Long author_id = author.getId();

        //#####
        if (influencer.getName().equals("탈퇴한 사용자 그룹") || author.getName().equals("탈퇴한 사용자 그룹")) {
            log.warn("paymentId = {} 탈퇴한 사용자에 대한 정산",payment.getId());
            return;
        }

        boolean success = false;
        try {
            balanceService.transfer(author_id, user_id, amount);
            success = true;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        finally {
            if (success)
                payment.paidUpdate(curViewCount, amount, LocalDateTime.now(), true);
            else
                payment.unPaidUpdate(0, false);
        }
      
        paymentRepository.save(payment);
    }

}
