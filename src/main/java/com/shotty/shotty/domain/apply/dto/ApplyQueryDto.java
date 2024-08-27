package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.bid.domain.Bid;

public record ApplyQueryDto(
        boolean bidded,
        boolean uploaded,
        boolean accepted
) {
    public static ApplyQueryDto from(Apply apply) {
        Bid bid = apply.getBid();

        boolean bidded = false;
        boolean uploaded = false;
        boolean accepted = false;

        if (bid != null) {
            bidded = true;

            uploaded = bid.getShortsId() != null;
            accepted = bid.getAccepted() != null && bid.getAccepted();
        }

        return new ApplyQueryDto(bidded, uploaded, accepted);
    }
}
