package com.coding.challenge.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Ein Service um die Http-Verbindung für GET und POST herzustellen.
 *
 * @author Dhalia
 */
@Service
public class HttpService {
    /**
     * Holt die Challenge Daten von der übergebenen Zieladresse mittels GET.
     *
     * @param urlChallenge Zieladresse
     * @return HttpResponse Objekt
     */
    public HttpResponse getChallenge(String urlChallenge) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlChallenge))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Sendet die Json Daten an die übergebene Adresse.
     *
     * @param urlSolution  Zieladresse
     * @param jsonSolution Daten im Json Format
     * @return Http Statuscode der Zieladresse
     */
    public HttpResponse sendSolutionToken(String urlSolution, String jsonSolution) {
        HttpRequest requestSolution = HttpRequest.newBuilder()
                .uri(URI.create(urlSolution))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSolution))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse responseSolution = null;
        try {
            responseSolution = client.send(requestSolution, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return responseSolution;
    }
}
