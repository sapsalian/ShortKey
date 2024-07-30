package com.shotty.shotty.domain.influencer.domain;

import com.shotty.shotty.domain.user.domain.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Influencer(String channelId, Long subscribers, User user) {
        this.channelId = channelId;
        this.subscribers = subscribers;
        this.user = user;
    }
}
