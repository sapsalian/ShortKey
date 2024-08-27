package com.shotty.shotty.domain.apply.enums;

import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.bid.domain.Bid;

public enum ApplyKindEnum {
    APPLIED,
    BIDDED,
    UNACCEPTED,
    ACCEPTED;

    public static ApplyKindEnum of(Apply apply) {
        Bid bid = apply.getBid();

        if (bid == null) {
            return APPLIED;
        }

        if (bid.getAccepted() != null && bid.getAccepted()) {
            return ACCEPTED;
        }

        if (bid.getShortsId() != null) {
            return UNACCEPTED;
        }

        return BIDDED;
    }
}
