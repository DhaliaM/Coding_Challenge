package com.coding.challenge.service;

import com.coding.challenge.ui.ChallengeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Challenge3 implements Challenge {
    HttpService httpService;

    public Challenge3(HttpService httpService) {
        this.httpService = httpService;
    }

    private static final int ID = 3;

    @Override
    public int getId() {
        return ID;
    }

    // A utility function to swap two elements
    private static void swap(Long[] arr, int i, int j) {
        Long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static Long getKthHighest(Long[] arr, int low, int high, int kthHighest) {
        // pivot
        int pivot = kthHighest;

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr[j] < pivot) {
                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);

        return (arr[kthHighest]);
    }

    @Override
    public ChallengeDto runChallenge() {

        String urlChallenge = "https://cc.the-morpheus.de/challenges/3/";
        HttpResponse<String> response = httpService.getChallengeRaw(urlChallenge);

        Challenge2Dto requestChallenge = null;

        try {
            requestChallenge = new ObjectMapper().readValue(response.body(), Challenge2Dto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Long> listAsLong = requestChallenge.getStringList().stream().map(x ->
                Long.valueOf(x)).collect(Collectors.toList());

        Long[] arr = new Long[listAsLong.size()];
        arr = listAsLong.toArray(arr);

        int start = 0;
        int end = arr.length - 1;
        int kthHighest = arr.length - Integer.parseInt(requestChallenge.getKey());

        Long result = getKthHighest(arr, start, end, kthHighest);

        String urlSolution = "https://cc.the-morpheus.de/solutions/3/";
        String jsonSolution = "{\"token\":" + result + "}";
        HttpResponse solution = httpService.sendSolutionToken(urlSolution, jsonSolution);

        ChallengeDto challengeDto = new ChallengeDto();
        if (solution.statusCode() == 200) {
            challengeDto.setResultChallenge(true);
            challengeDto.setChallengeId(ID);
        }

        return challengeDto;
    }
}
