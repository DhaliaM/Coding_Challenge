package com.coding.challenge.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HttpService {
    public HttpResponse getChallengeRaw(String urlChallenge){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlChallenge))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse sendSolutionToken(String urlSolution, String jsonSolution){
        HttpRequest requestSolution = HttpRequest.newBuilder()
                .uri(URI.create(urlSolution))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSolution))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse responseSolution = null;
        try {
            responseSolution = client.send(requestSolution, HttpResponse.BodyHandlers.discarding());
//            LOGGER.error(String.valueOf(responseSolution.statusCode()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseSolution;
    }
}
