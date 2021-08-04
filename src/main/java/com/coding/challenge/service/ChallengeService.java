package com.coding.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChallengeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChallengeService.class);

    public ChallengeService(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    List<Challenge> challenges;

    public void selectedChallenge(int id){

        for(Challenge challenge : challenges){
            if (id == challenge.getId()){
                challenge.runChallenge();
            }
        }
    }

}
