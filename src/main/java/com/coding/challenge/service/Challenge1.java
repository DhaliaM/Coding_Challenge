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
    private Benchmark benchmark;

    public Challenge1(HttpService httpService, Benchmark benchmark) {
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/1/";
        HttpResponse response = httpService.getChallenge(urlChallenge);
        benchmark.firstExclusiveBenchmark(false);

        benchmark.secondExclusiveBenchmark(true);
        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonResult = (String) response.body();
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);
        benchmark.secondExclusiveBenchmark(false);

        benchmark.runBenchmark(false);
        benchmark.reset();

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setChallengeId(ID);
            challengeDto.setResultChallenge(true);
            challengeDto.setFunctionTime(benchmark.getBenchmarkTime());
            challengeDto.setServerGetTime(benchmark.firstExclusiveBenchmarkTime);
            challengeDto.setServerPostTime(benchmark.secondExclusiveBenchmarkTime);
        }

        return challengeDto;
    }

}
