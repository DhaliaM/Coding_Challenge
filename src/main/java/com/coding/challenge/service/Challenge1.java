package com.coding.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
@Component
public class Challenge1 implements Challenge{
    private static final Logger LOGGER = LoggerFactory.getLogger(Challenge1.class);
    private static final int ID = 1;

    public Challenge1(HttpService httpService) {
        this.httpService = httpService;
    }

    HttpService httpService;

    @Override
    public int getId() {
        return ID;
    }

    public void runChallenge() {

        String urlChallenge = "https://cc.the-morpheus.de/challenges/1/";
        HttpResponse<String> response = httpService.getChallengeRaw(urlChallenge);

        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonSolution = "{\"token\":" + response.body()+ "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution,jsonSolution);

        LOGGER.error(String.valueOf(solution.statusCode()));

    }

}
