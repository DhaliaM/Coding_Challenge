package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse absolviert die 6. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/6/" über GET
 * zu extrahieren, die erhaltene Dezimalzahl in binär umwandeln und das Ergebnis zurück an
 * "https://cc.the-morpheus.de/solutions/6/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge6 implements Challenge {
    private static final int ID = 6;
    HttpService httpService;

    public Challenge6(HttpService httpService) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/6/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        String challengeData = response.body();

        long decimalData = Long.parseLong(challengeData);
        List<Integer> listedRemainder = new ArrayList<>();
        while (decimalData > 0) {
            listedRemainder.add((int) (decimalData % 2));
            decimalData = decimalData / 2;
        }

        List<Integer> reverseList = listedRemainder.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        String jsonResult = reverseList.toString();

        String urlSolution = "https://cc.the-morpheus.de/solutions/6/";

        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
