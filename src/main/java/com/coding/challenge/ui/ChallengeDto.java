package com.coding.challenge.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ein DTO für den Informationsaustausch zwischen Webseite und Server.
 *
 * @author Dhalia
 */
public class ChallengeDto {
    @JsonProperty("challenge")
    private int challengeId;

    @JsonProperty("result")
    private boolean resultChallenge;

    @JsonProperty("usedTime")
    private float usedTime;

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
                ", resultChallenge=" + resultChallenge +
                ", usedTime=" + usedTime +
                '}';
    }

}
