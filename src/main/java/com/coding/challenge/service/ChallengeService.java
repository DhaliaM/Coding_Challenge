package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChallengeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChallengeService.class);

    public ChallengeService(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    List<Challenge> challenges;

    public ChallengeDto selectedChallenge(int id){
        ChallengeDto challengeDto = null;
        for(Challenge challenge : challenges){
            if (id == challenge.getId()){
                challengeDto = challenge.runChallenge();
            }
        }
        return challengeDto;
    }

}
