package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

/**
 * Diese Klasse absolviert die 1. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/1/" über GET
 * zu extrahieren, und diesen an "https://cc.the-morpheus.de/solutions/1/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge1 implements Challenge {
    private static final int ID = 1;
    private final HttpService httpService;

    public Challenge1(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public int getId() {
        return ID;
    }

    /**
     * Startet die Challenge.
     *
     * @return Objekt vom Typ ChallengeDto
     */
    @Override
    public ChallengeDto runChallenge() {
        String urlChallenge = "https://cc.the-morpheus.de/challenges/1/";
        HttpResponse response = httpService.getChallenge(urlChallenge);

        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonSolution = "{\"token\":" + response.body() + "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonSolution);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setChallengeId(ID);
            challengeDto.setResultChallenge(true);
        }

        return challengeDto;
    }

}
