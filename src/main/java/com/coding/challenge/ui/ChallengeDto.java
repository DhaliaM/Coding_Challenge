package com.coding.challenge.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengeDto {

    @JsonProperty("Challenge")
    private int challengeId = 0;

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    @Override
    public String toString() {
        return "ChallengeDto{" +
                "challengeId=" + challengeId +
                '}';
    }

}
