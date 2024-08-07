package com.shotty.shotty.domain.influencer.exception.custom_exception;

public class InfluencerNotFoundException extends RuntimeException {
    public InfluencerNotFoundException(){
        super("Influencer not found");
    }
    public InfluencerNotFoundException(String message) {
        super(message);
    }
}
