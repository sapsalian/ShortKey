package com.shotty.shotty.domain.bid.domain;

import com.shotty.shotty.domain.apply.domain.Apply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "apply_id")
    private Apply apply;

    private String shortsId;

    public Bid(Apply apply) {
        this.apply = apply;
        shortsId = null;
    }

    public void setShortsId(String shortsId) {
        this.shortsId = shortsId;
    }
}
