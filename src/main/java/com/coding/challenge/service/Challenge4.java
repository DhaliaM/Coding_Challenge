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
    Benchmark benchmark;

    public Challenge4(HttpService httpService, Benchmark benchmark) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/4/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        benchmark.firstExclusiveBenchmark(false);

        List<String> challengeDataList = null;
        int rotations = 0;
        try {
            GetChallengeDto challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
            challengeDataList = challengeData.getStringList();
            rotations = Integer.parseInt(challengeData.getKey());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert challengeDataList != null;
        Collections.rotate(challengeDataList, rotations);

        String jsonResult = null;
        try {
            jsonResult = new ObjectMapper().writeValueAsString(challengeDataList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        benchmark.secondExclusiveBenchmark(true);
        String urlSolution = "https://cc.the-morpheus.de/solutions/4/";
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
