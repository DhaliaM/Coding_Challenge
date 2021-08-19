package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Diese Klasse absolviert die 8. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/8/" über GET
 * zu extrahieren. Die Indizes der ersten vier Zahlen ermitteln, deren Summe der gesuchten Zahl entsprechen. Und diese
 * zurück an "https://cc.the-morpheus.de/solutions/8/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge8 implements Challenge {
    private static final int ID = 8;
    HttpService httpService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Challenge8.class);

    public Challenge8(HttpService httpService) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/8/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        GetChallengeDto challengeData = null;
        int key =0;
        try {
            challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
            key = Integer.parseInt(challengeData.getKey());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert challengeData != null;
        List<Integer> listSearchData = challengeData.getStringList().stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        int firstOperandIndex = 0;
        int secondOperandIndex = 0;
        int thirdOperandIndex = 0;
        int fourthOperandIndex = 0;
        for (int i = 0; i < listSearchData.size(); i++) {
            for (int j = i + 1; j < listSearchData.size(); j++) {
                for (int k = j + 1; k < listSearchData.size(); k++) {
                    for (int l = k + 1; l < listSearchData.size(); l++) {
                        if (listSearchData.get(i) + listSearchData.get(j) + listSearchData.get(k) + listSearchData.get(l) == key) {
                            firstOperandIndex = i;
                            secondOperandIndex = j;
                            thirdOperandIndex = k;
                            fourthOperandIndex = l;
                        }
                    }
                }
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(firstOperandIndex);
        result.add(secondOperandIndex);
        result.add(thirdOperandIndex);
        result.add(fourthOperandIndex);

        LOGGER.error(String.valueOf(key));
        LOGGER.error(String.valueOf(listSearchData.get(firstOperandIndex)+listSearchData.get(secondOperandIndex)+listSearchData.get(thirdOperandIndex)+listSearchData.get(fourthOperandIndex)));


        String urlSolution = "https://cc.the-morpheus.de/solutions/8/";
        String jsonResult = result.toString();
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);
        LOGGER.error(jsonResult);
        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
