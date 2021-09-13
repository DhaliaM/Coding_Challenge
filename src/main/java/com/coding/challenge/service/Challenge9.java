package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse absolviert die 9. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/9/" über GET
 * zu extrahieren. Die Indizes der beiden Zahlen ermitteln, deren Summe der gesuchten Zahl entsprechen. Und das
 * Index Paar mit dem geringsten Abstand zueinander zurück an "https://cc.the-morpheus.de/solutions/9/" via POST
 * zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge9 implements Challenge {
    private static final int ID = 9;
    HttpService httpService;
    Benchmark benchmark;

    public Challenge9(HttpService httpService, Benchmark benchmark) {
        this.httpService = httpService;
        this.benchmark = benchmark;
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
        benchmark.runBenchmark(true);

        benchmark.firstExclusiveBenchmark(true);
        String urlChallenge = "https://cc.the-morpheus.de/challenges/9/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        benchmark.firstExclusiveBenchmark(false);

        GetChallengeDto challengeData = null;
        int key = 0;
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

        ArrayList<Integer> listSummands = new ArrayList<>();
        for (int i = 0; i < listSearchData.size(); i++) {
            for (int j = 0; j < listSearchData.size(); j++) {
                if (listSearchData.get(i) + listSearchData.get(j) == key) {
                    listSummands.add(i);
                    listSummands.add(j);
                }
            }
        }

        ArrayList<Integer> closestSummands = new ArrayList<>();
        closestSummands.add(listSummands.get(0));
        closestSummands.add(listSummands.get(1));
        int difference = listSummands.get(0) - listSummands.get(1);
        difference = Math.abs(difference);
        for (int i = 2; i < listSummands.size() - 2; i += 2) {

            if (difference > Math.abs(listSummands.get(i + 1) - listSummands.get(i))) {
                difference = Math.abs(listSummands.get(i + 1) - listSummands.get(i));
                closestSummands.set(0, listSummands.get(i));
                closestSummands.set(1, listSummands.get(i + 1));
            }
        }

        benchmark.secondExclusiveBenchmark(true);
        String urlSolution = "https://cc.the-morpheus.de/solutions/9/";
        String jsonResult = closestSummands.toString();
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);
        benchmark.secondExclusiveBenchmark(false);

        benchmark.runBenchmark(false);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
            challengeDto.setFunctionTime(benchmark.getBenchmarkTime());
            challengeDto.setServerGetTime(benchmark.getFirstExclusiveBenchmarkTime());
            challengeDto.setServerPostTime(benchmark.getSecondExclusiveBenchmarkTime());
        }
        benchmark.reset();

        return challengeDto;
    }
}
