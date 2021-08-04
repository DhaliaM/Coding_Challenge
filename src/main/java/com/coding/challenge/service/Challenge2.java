package com.coding.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.net.http.HttpResponse;
@Component
public class Challenge2 implements Challenge{
    private static final int ID = 2;
    HttpService httpService;

    public Challenge2(HttpService httpService) {
        this.httpService = httpService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Challenge2.class);

    @Override
    public int getId() {
        return ID;
    }

    public void runChallenge() {

        String urlChallenge = "https://cc.the-morpheus.de/challenges/2/";
        HttpResponse<String> response = httpService.getChallengeRaw(urlChallenge);

    }
}
