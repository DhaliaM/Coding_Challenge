package com.coding.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Challenge1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Challenge1.class);

    public void challenge1() {
        HttpClient client = HttpClient.newHttpClient();
        String urlChallenge = "https://cc.the-morpheus.de/challenges/1/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlChallenge))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
           LOGGER.error(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String urlSolution = "https://cc.the-morpheus.de/solutions/1/";
        String jsonSolution = "{\"token\":" + response.body()+ "}";
        HttpRequest requestSolution = HttpRequest.newBuilder()
                .uri(URI.create(urlSolution))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSolution))
                .build();

        try {
            HttpResponse<?> responseSolution = client.send(requestSolution, HttpResponse.BodyHandlers.discarding());
            LOGGER.error(String.valueOf(responseSolution.statusCode()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
