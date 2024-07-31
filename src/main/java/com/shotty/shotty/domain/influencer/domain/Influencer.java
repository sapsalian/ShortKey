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
    @Id
    @GeneratedValue
    private Long id;

    private String channelId;

    private Long subscribers;

    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Niche niche;

    private String profile_image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Influencer(User user, String profile_image, Niche niche, Long subscribers, String channelId, boolean verified) {
        this.user = user;
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
