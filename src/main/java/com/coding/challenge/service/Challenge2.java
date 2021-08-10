package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;


@Component
public class Challenge2 implements Challenge {
    private static final int ID = 2;
    HttpService httpService;

    public Challenge2(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public int getId() {
        return ID;
    }

    public ChallengeDto runChallenge() {

        ChallengeDto challengeDto = new ChallengeDto();
        int index = 0;
        long executionTime; // Zeit zum durchsuchen der Liste, ohne Server Zeiten
        long startTime;
        long endTime;
        long requestTime = 0;
        Long requestStartTime, requestStopTime;

        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            requestStartTime = System.nanoTime();
            String urlChallenge = "https://cc.the-morpheus.de/challenges/2/";
            HttpResponse<String> response = httpService.getChallengeRaw(urlChallenge);
            requestStopTime = System.nanoTime();
            requestTime = requestTime + (requestStopTime - requestStartTime);

            try {
                Challenge2Dto result = new ObjectMapper().readValue(response.body(), Challenge2Dto.class);
                index = result.getStringList().indexOf(result.getKey());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();
        executionTime = (endTime - startTime - requestTime) / 100;

        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonSolution = "{\"token\":" + index + "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonSolution);

        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
            challengeDto.setUsedTime((float) executionTime / 1000000);
        }
        return challengeDto;
    }
}
