package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse absolviert die 4. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/4/" über GET
 * zu extrahieren, die enthaltene List k-mal zu rotieren, und zurück an "https://cc.the-morpheus.de/solutions/4/"
 * via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge4 implements Challenge {
    final static int ID = 4;
    HttpService httpService;

    public Challenge4(HttpService httpService) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/4/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);

        List<String> challengeDataList = null;
        int rotations = 0;
        try {
            GetChallengeDto challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
            challengeDataList = challengeData.getStringList();
            rotations = Integer.parseInt(challengeData.getKey());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Collections.rotate(challengeDataList, rotations);

        String jsonList = null;
        try {
            jsonList = new ObjectMapper().writeValueAsString(challengeDataList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String urlSolution = "https://cc.the-morpheus.de/solutions/4/";
        String jsonSolution = "{\"token\":" + jsonList + "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonSolution);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
