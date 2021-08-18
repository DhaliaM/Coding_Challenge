package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Diese Klasse absolviert die 7. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/5/" über GET
 * zu extrahieren. Die Indizes der ersten beiden Zahlen ermitteln, deren Summe der gesuchten Zahl entsprechen. Und diese
 * zurück an "https://cc.the-morpheus.de/solutions/5/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge7 implements Challenge {
    private static final int ID = 7;
    HttpService httpService;

    public Challenge7(HttpService httpService) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/7/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        GetChallengeDto challengeData = null;
        try {
            challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        int key = Integer.parseInt(challengeData.getKey());
        List<Integer> listSearchData = challengeData.getStringList().stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        int firstOperandIndex = 0;
        int secondOperandIndex = 0;

        Map<Integer, Integer> checkedNumbers = new HashMap<>();

        for (int i = 0; i < listSearchData.size(); i++) {
            int complement = key - listSearchData.get(i);
            if (checkedNumbers.containsKey(complement)) {
                firstOperandIndex = i;
                secondOperandIndex = checkedNumbers.get(complement);
            } else {
                checkedNumbers.put(listSearchData.get(i), i);
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(firstOperandIndex);
        result.add(secondOperandIndex);

        String urlSolution = "https://cc.the-morpheus.de/solutions/7/";
        String jsonResult = result.toString();
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
