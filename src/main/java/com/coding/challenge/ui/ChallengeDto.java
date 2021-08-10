package com.coding.challenge.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengeDto {

    @JsonProperty("challenge")
    private int challengeId = 0;

    @JsonProperty("result")
    private boolean resultChallenge = false;

    @JsonProperty("usedTime")
    private float usedTime = 0;

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public boolean getResultChallenge() {
        return resultChallenge;
    }

    public void setResultChallenge(boolean resultChallenge) {
        this.resultChallenge = resultChallenge;
    }

    public float getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(float usedTime) {
        this.usedTime = usedTime;
    }

    @Override
    public String toString() {
        return "ChallengeDto{" +
                "challengeId=" + challengeId +
                '}';
    }

}
