package GitHubUserActivity.service;

import GitHubUserActivity.model.Event;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiManager {

    public List<Event> getUsernameActivity(String username) {
        // Retornar lista de eventos do usuario
        String json = fetchApiData(username);

        if (json == null) {
            return new ArrayList<>();
        }
        return parseApiData(json);
    }

    private String fetchApiData(String username) {
        String url = "https://api.github.com/users/" + username + "/events";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                System.out.println("User not found.");
                return null;
            } else {
                System.out.println("Api Error. HTTP code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            System.out.println("Connection Error!");
            return null;
        }
    }

    private List<Event> parseApiData(String json) {
        List<Event> events = new ArrayList<>();
        System.out.println("Json: " + json);
        return events;
    }
}