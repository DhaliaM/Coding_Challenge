package com.coding.challenge.service;

import org.springframework.stereotype.Service;

@Service
public class ChallengeService {
    public void selectedChallenge(int id){
        // select ID and start challenge
        if(id == 1) {
            Challenge1 challenge = new Challenge1();
            challenge.challenge1();
        }

    }
}
