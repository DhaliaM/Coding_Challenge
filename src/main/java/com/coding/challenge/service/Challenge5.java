package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
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

    public Challenge5(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public int getId() {
        return ID;
    }

    /**
     * Eine Hilfsfunktion um einen String mit mathematischen Operatoren zu vergleichen, und die entsprechende
     * Operation durchzuführen.
     *
     * @param a        erster Operand
     * @param b        zweiter Operand
     * @param operator String welcher den Operator enthält
     * @return Ergebnis vom Typ Float
     */
    private Float calculate(Float a, Float b, String operator) {
        Float result = null;
        if (Objects.equals(operator, "+")) {
            result = a + b;
        }
        if (Objects.equals(operator, "-")) {
            result = a - b;
        }
        if (Objects.equals(operator, "/")) {
            result = a / b;
        }
        if (Objects.equals(operator, "*")) {
            result = a * b;
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
        String urlChallenge = "https://cc.the-morpheus.de/challenges/5/";
        HttpResponse<String> response = httpService.getChallenge(urlChallenge);
        String challengeData = response.body();

        List<String> listedData = Stream.of(challengeData.split(" "))
                .collect(Collectors.toList());

        Stack<Float> stack = new Stack<>();
        for (String o : listedData) {
            if (o.matches("-?(0|[1-9]\\d*)")) { // o ist eine Zahl
                int n = Integer.parseInt(o);
                stack.push((float) n);
            }
            if (!o.matches("-?(0|[1-9]\\d*)")) { // o ist keine Zahl
                float a = stack.pop();
                float b = stack.pop();
                float c = calculate(a, b, o);
                stack.push(c);
            }
        }
        int result = stack.pop().intValue();

        String urlSolution = "https://cc.the-morpheus.de/solutions/5/";
        String jsonResult = String.valueOf(result);
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonResult);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
