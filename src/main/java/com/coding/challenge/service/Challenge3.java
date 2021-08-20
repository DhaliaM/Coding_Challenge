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
    private Benchmark benchmark;

    public Challenge3(HttpService httpService, KthSearchAlgorithm kthSearchAlgorithm, Benchmark benchmark) {
        this.httpService = httpService;
        this.kthSearchAlgorithm = kthSearchAlgorithm;
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/3/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        benchmark.firstExclusiveBenchmark(false);

        GetChallengeDto challengeData = null;
        try {
            challengeData = new ObjectMapper().readValue(response.body(), GetChallengeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Long result = null;
        if (challengeData != null) {
            List<Long> listAsLong = challengeData.getStringList()
                    .stream()
                    .map(Long::valueOf).collect(Collectors.toList());

            Long[] arr = new Long[listAsLong.size()];
            arr = listAsLong.toArray(arr);

            int start = 0;
            int end = arr.length - 1;
            int kthHighest = arr.length - Integer.parseInt(challengeData.getKey());

            result = kthSearchAlgorithm.getKthHighest(arr, start, end, kthHighest);
        }

        benchmark.secondExclusiveBenchmark(true);
        String jsonResult = String.valueOf(result);
        String urlSolution = "https://cc.the-morpheus.de/solutions/3/";
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
