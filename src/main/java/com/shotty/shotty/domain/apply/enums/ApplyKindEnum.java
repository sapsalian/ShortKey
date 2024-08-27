package com.shotty.shotty.domain.apply.enums;

import com.shotty.shotty.domain.apply.dto.ApplyQueryDto;

public enum ApplyKindEnum {
    APPLIED,
    BIDDED,
    UNACCEPTED,
    ACCEPTED;

    public static ApplyKindEnum from(ApplyQueryDto applyQueryDto) {
        if (applyQueryDto == null) {
            return APPLIED;
        }

        if (applyQueryDto.accepted()) {
            return ACCEPTED;
        }

        if (applyQueryDto.uploaded()) {
            return UNACCEPTED;
        }

        if (applyQueryDto.bidded()) {
            return BIDDED;
        }

        return APPLIED;
    }
}
