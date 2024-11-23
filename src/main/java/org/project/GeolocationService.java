package org.project;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeolocationService {

    public static JsonObject getLocationData(String city) {
        city = city.replaceAll(" ", "+");

        String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                + city + "&count=1&language=ru&format=json";

        // Получение ответа от API геолокации и проверка ее работоспособности
        var apiResponse = HttpUtils.fetchApiResponse(url, Config.YANDEX_API_KEY);
        if (apiResponse == null || apiResponse.statusCode() != 200) {
            System.out.println("Ошибка подключения: HTTP " + (apiResponse != null ? apiResponse.statusCode() : "Нет ответа"));
            return null;
        }

        // Парсинг данных геолокации и проверка на наличие данных по городу
        try {
            var geolocationData = JsonParser.parseString(apiResponse.body())
                    .getAsJsonObject();
            if (!geolocationData.has("results")
                    || geolocationData.getAsJsonArray("results").isEmpty()) {
                System.out.println("Город не найден!");
                return null;
            }
            // System.out.println(geolocationData.getAsJsonArray("results").get(0).getAsJsonObject()); Проверка Json файла
            return geolocationData.getAsJsonArray("results").get(0).getAsJsonObject();
        } catch (Exception ex) {
            System.out.println("Ошибка обработки данных геолокации: " + ex.getMessage());
        }

        // Произошла ошибка
        return null;
    }
}