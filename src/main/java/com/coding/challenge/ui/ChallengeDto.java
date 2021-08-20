package com.coding.challenge.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ein DTO für den Informationsaustausch zwischen Webseite und Server.
 *
 * @author Dhalia
 */
public class ChallengeDto {
    @JsonProperty("challengeId")
    private int challengeId;

    @JsonProperty("result")
    private boolean resultChallenge;

    @JsonProperty("functionTime")
    private float functionTime;

    @JsonProperty("GET_Time")
    private float serverGetTime;

    @JsonProperty("POST_Time")
    private float serverPostTime;

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

    public float getFunctionTime() {
        return functionTime;
    }

    public void setFunctionTime(float functionTime) {
        this.functionTime = functionTime;
    }

    public float getServerGetTime() {
        return serverGetTime;
    }

    public void setServerGetTime(float serverGetTime) {
        this.serverGetTime = serverGetTime;
    }

    public float getServerPostTime() {
        return serverPostTime;
    }

    public void setServerPostTime(float serverPostTime) {
        this.serverPostTime = serverPostTime;
    }

    @Override
    public String toString() {
        return "ChallengeDto{" +
                "challengeId=" + challengeId +
                ", resultChallenge=" + resultChallenge +
                ", functionTime=" + functionTime +
                ", serverGetTime=" + serverGetTime +
                ", serverPostTime=" + serverPostTime +
                '}';
    }

}
