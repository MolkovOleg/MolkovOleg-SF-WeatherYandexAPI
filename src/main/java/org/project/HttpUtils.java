package org.project;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {

    // Создаем HttpClient
    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpResponse<String> fetchApiResponse(String url, String apiKey) {
        // Создаем запрос по переданной ссылке
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-API-Key", apiKey)
                .header("User-Agent", "WeatherApp/1.0")
                .GET()
                .build();

        try {
            // Отправление запроса и получение ответа
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            System.out.println("Ошибка при подключении: " + ex.getMessage());
        }

        // Не может подключиться
        return null;
    }
}