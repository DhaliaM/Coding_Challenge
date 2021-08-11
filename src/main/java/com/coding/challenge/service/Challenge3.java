package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse absolviert die 3. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/3/" über GET
 * zu extrahieren, und die Kth höchste Zahl zurück an "https://cc.the-morpheus.de/solutions/3/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge3 implements Challenge {
    private static final int ID = 3;
    private final HttpService httpService;
    private final KthSearchAlgorithm kthSearchAlgorithm;

    public Challenge3(HttpService httpService, KthSearchAlgorithm kthSearchAlgorithm) {
        this.httpService = httpService;
        this.kthSearchAlgorithm = kthSearchAlgorithm;
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/3/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);

        GetChallengeDto requestChallenge = null;

        try {
            requestChallenge = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Long result = null;
        if (requestChallenge != null) {
            List<Long> listAsLong = requestChallenge.getStringList()
                    .stream()
                    .map(Long::valueOf).collect(Collectors.toList());

            Long[] arr = new Long[listAsLong.size()];
            arr = listAsLong.toArray(arr);

            int start = 0;
            int end = arr.length - 1;
            int kthHighest = arr.length - Integer.parseInt(requestChallenge.getKey());

            result = kthSearchAlgorithm.getKthHighest(arr, start, end, kthHighest);
        }

        String jsonResult = String.valueOf(result);
        String urlSolution = "https://cc.the-morpheus.de/solutions/3/";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
