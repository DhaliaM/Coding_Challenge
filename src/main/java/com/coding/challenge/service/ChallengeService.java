package com.coding.challenge.service;

import org.springframework.stereotype.Service;



@Service
public class ChallengeService {
    HttpService httpService;

    public ChallengeService(HttpService httpService) {
        this.httpService = httpService;
    }


    public void selectedChallenge(int id){
        // select ID and start challenge
        if(id == 1) {
            Challenge challenge = new Challenge1(httpService);
            challenge.runChallenge();
        }

        if(id == 2){
            Challenge challenge = new Challenge2(httpService);
            challenge.runChallenge();
        }

    }

}
