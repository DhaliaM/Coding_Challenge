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
    private Benchmark benchmark;
    public Challenge2(HttpService httpService, Benchmark benchmark) {
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

        int index = 0;
        int numberOfRuns = 1;
        for (int i = 0; i < numberOfRuns; i++) {

            benchmark.firstExclusiveBenchmark(true);
            String urlChallenge = "https://cc.the-morpheus.de/challenges/2/";
            HttpResponse<String> response = httpService.getChallenge(urlChallenge);
            benchmark.firstExclusiveBenchmark(false);

            try {
                GetChallengeDto challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
                index = challengeData.getStringList().indexOf(challengeData.getKey());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        benchmark.secondExclusiveBenchmark(true);
        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonResult = String.valueOf(index);
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
