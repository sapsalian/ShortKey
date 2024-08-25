package com.shotty.shotty.domain.payment.application;

import com.shotty.shotty.youtube.dto.video.ShortsSimpleInfoDto;
import com.shotty.shotty.YoutubeService;
import com.shotty.shotty.domain.bid.dao.BidRepository;
import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.payment.dao.PaymentRepository;
import com.shotty.shotty.domain.payment.domain.Payment;
import com.shotty.shotty.youtube.dto.video.YouTubeVideoItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final BidRepository bidrepository;
    private final YoutubeService youtubeService;
    private final PaymentRepository paymentRepository;

    private static final float PRICE_PER_VIEW = 0.1f;

    public void doPayment() {
        List<Bid> acceptedBids = bidrepository.findAcceptedBids();

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
            Payment payment = paymentMap.get(bid.getId());
            YouTubeVideoItem youTubeVideoItem = videoItemMap.get(bid.getShortsId());
            int curViewCount = Integer.parseInt(youTubeVideoItem.getStatistics().getViewCount());
            //정상 로직
            int increment = curViewCount - payment.getLastViewCount();
            float amount = increment * PRICE_PER_VIEW;

            //ToDo 입출금

            payment.paidUpdate(curViewCount,amount, LocalDateTime.now(),true);

            //ToDo 비정상 로직
            //payment.unPaidUpdate(0, false);

        }


    }

}
