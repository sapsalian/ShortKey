package com.shotty.shotty.domain.influencer.domain;

import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "influencers")
public class Influencer {
    @Id @Column(name = "influencer_id")
    @GeneratedValue
    private Long id;

    private String channelId;

    private Long subscribers;

    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Niche niche;

    private String profile_image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //연관관계 메서드 작성
    private void setUserAsInfluencer(User user) {
        this.user = user;
        user.changeRole(UserRoleEnum.INFLUENCER);
    }

    private Influencer(User user, String profile_image, Niche niche, Long subscribers, String channelId, boolean verified) {
        this.setUserAsInfluencer(user);
        this.profile_image = profile_image;
        this.niche = niche;
        this.subscribers = subscribers;
        this.channelId = channelId;
        this.verified = verified;
    }

    public static Influencer from(User user, SaveInfluencerDto saveInfluencerDto) {
        return new Influencer(
                user,
                saveInfluencerDto.getProfile_image(),
                saveInfluencerDto.getNiche(),
                saveInfluencerDto.getSubscribers(),
                saveInfluencerDto.getChannelId(),
                false
        );
    }

}
