package com.coding.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.http.HttpResponse;

public class Challenge2 implements Challenge{

    HttpService httpService;

    public Challenge2(HttpService httpService) {
        this.httpService = httpService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Challenge2.class);

    public void runChallenge() {

        String urlChallenge = "https://cc.the-morpheus.de/challenges/2/";
        HttpResponse<String> response = httpService.getChallengeRaw(urlChallenge);



    }
}
