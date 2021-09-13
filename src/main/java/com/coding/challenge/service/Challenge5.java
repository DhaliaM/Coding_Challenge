package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Diese Klasse absolviert die 5. Challenge.
 * Die Challenge besteht darin die Daten von der Rest-SST "https://cc.the-morpheus.de/challenges/5/" über GET
 * zu extrahieren, eine Berechnung in Postfix Notation durchzuführen und das Ergebnis als int(abgerundet) zurück an
 * "https://cc.the-morpheus.de/solutions/5/" via POST zu senden.
 *
 * @author Dhalia
 */
@Component
public class Challenge5 implements Challenge {
    private final static int ID = 5;
    HttpService httpService;
    Benchmark benchmark;

    public Challenge5(HttpService httpService, Benchmark benchmark) {
        this.httpService = httpService;
        this.benchmark = benchmark;
    }

    @Override
    public int getId() {
        return ID;
    }

    /**
     * Eine Hilfsfunktion um einen String mit mathematischen Operatoren zu vergleichen, und die entsprechende
     * Operation durchzuführen.
     *
     * @param firstOperand        erster Operand
     * @param secondOperand        zweiter Operand
     * @param operator String welcher den Operator enthält
     * @return Ergebnis vom Typ Float
     */
    private Float calculate(Float firstOperand, Float secondOperand, String operator) {
        Float result = null;
        if (Objects.equals(operator, "+")) {
            result = firstOperand + secondOperand;
        }
        if (Objects.equals(operator, "-")) {
            result = firstOperand - secondOperand;
        }
        if (Objects.equals(operator, "/")) {
            result = firstOperand / secondOperand;
        }
        if (Objects.equals(operator, "*")) {
            result = firstOperand * secondOperand;
        }

        return result;
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/5/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        benchmark.firstExclusiveBenchmark(false);

        String challengeData = response.body();
        List<String> listedData = Stream.of(challengeData.split(" "))
                .collect(Collectors.toList());

        Deque<Float> deque = new ArrayDeque<>();
        for (String o : listedData) {
            if (o.matches("-?(0|[1-9]\\d*)")) { // o ist eine Zahl
                int n = Integer.parseInt(o);
                deque.offerFirst((float) n);
            }
            if (!o.matches("-?(0|[1-9]\\d*)")) { // o ist keine Zahl
                float firstOperand = deque.pollFirst();
                float secondOperand = deque.pollFirst();
                float postfixResult = calculate(firstOperand, secondOperand, o);
                deque.offerFirst(postfixResult);

            }
        }
        float postfixResult = deque.pollFirst();

        benchmark.secondExclusiveBenchmark(true);
        String urlSolution = "https://cc.the-morpheus.de/solutions/5/";
        String jsonResult = String.valueOf((int)postfixResult);
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
