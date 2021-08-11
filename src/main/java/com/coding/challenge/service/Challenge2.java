package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * Diese Klasse absolviert die 2. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/2/" über GET
 * zu extrahieren, und den Inhalt des gesuchten Index in einer Liste an "https://cc.the-morpheus.de/solutions/2/"
 * via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge2 implements Challenge {
    private static final int ID = 2;
    private final HttpService httpService;

    public Challenge2(HttpService httpService) {
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
        long requestTime = 0;
        long startTime = System.nanoTime();
        int index = 0;
        int numberOfRuns = 100;
        for (int i = 0; i < numberOfRuns; i++) {
            long requestStartTime = System.nanoTime();
            String urlChallenge = "https://cc.the-morpheus.de/challenges/2/";
            HttpResponse<String> response = httpService.getChallenge(urlChallenge);
            long requestStopTime = System.nanoTime();
            requestTime = requestTime + (requestStopTime - requestStartTime);

            try {
                GetChallengeDto result = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
                index = result.getStringList().indexOf(result.getKey());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime - requestTime) / numberOfRuns; // Zeit zum durchsuchen der Liste, ohne Server Zeiten

        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonSolution = "{\"token\":" + index + "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonSolution);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            int nanoTimeToMs = 1000000;
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
            challengeDto.setUsedTime((float) executionTime / nanoTimeToMs);
        }
        return challengeDto;
    }
}
