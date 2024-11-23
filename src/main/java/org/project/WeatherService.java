package org.project;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class WeatherService {

    public static void displayWeatherData(double latitude, double longitude) {
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + latitude + "&lon=" + longitude + "&limit=3";

        // Получение ответа от API погоды и проверка ее работоспособности
        var apiResponse = HttpUtils.fetchApiResponse(url, Config.YANDEX_API_KEY);
        if (apiResponse == null || apiResponse.statusCode() != 200) {
            System.out.println("Ошибка подключения: HTTP " + (apiResponse != null ? apiResponse.statusCode() : "Нет ответа"));
            return;
        }

        // 1. Преобразование Json файла и получение всех данных погоды
        System.out.println("========================================================");
        System.out.println("1. Полная информация о погоде в виде Json:");
        System.out.println(prettyJson(apiResponse.body()));


        // Парсинг данных погоды
        try {

            // Парсинг текущей погоды
            JsonObject currentWeatherJson = (JsonObject) JsonParser.parseString(apiResponse.body())
                    .getAsJsonObject()
                    .get("fact");

            // 2. Получение текущей температуры и дополнительных данных
            System.out.println("========================================================");
            System.out.println("2. Текущая температура и дополнительные данные о погоде:");
            System.out.printf("Текущая Температура: %.1f °C\n", currentWeatherJson.get("temp")
                    .getAsDouble());
            System.out.printf("Относительная Влажность: %.1f %%\n", currentWeatherJson.get("humidity")
                    .getAsDouble());
            System.out.printf("Время года: %s\n", currentWeatherJson.get("season")
                    .getAsString());
            System.out.printf("Погода данный момент (скорость ветра): %.1f м/с\n", currentWeatherJson.get("wind_speed")
                    .getAsDouble());

            // Парсинг прогнозов погоды
            JsonArray forecastsWeatherJson = (JsonArray) JsonParser.parseString(apiResponse.body())
                    .getAsJsonObject()
                    .get("forecasts");

            // Список для хранения средней температуры по дням
            List<Double> dailyAvgTemp = new ArrayList<>();
            String startDate = ""; // Начальная дата
            String endDate = ""; // Конечная дата

            // 3. Вычисление средней температуры
            System.out.println("========================================================");
            System.out.println("3. Расчет средней температуры в интервале дней:");

            // Итерируемся по всем полученным прогнозам
            for (int i = 0; i < forecastsWeatherJson.size(); i++) {
                JsonObject forecast = forecastsWeatherJson.get(i).getAsJsonObject();

                // Получение даты прогноза
                String date = forecast.get("date").getAsString();

                // Устанавливаем начальную дату
                if (i == 0) {
                    startDate = date;
                }

                // Устанавливаем конечную дату
                if (i == forecastsWeatherJson.size() - 1) {
                    endDate = date;
                }

                // Получаем данные температуры в течении одного дня
                JsonObject parts = forecast.getAsJsonObject("parts");
                double tempMorning = parts.getAsJsonObject("morning").get("temp_avg").getAsDouble();
                double tempDay = parts.getAsJsonObject("day").get("temp_avg").getAsDouble();
                double tempEvening = parts.getAsJsonObject("evening").get("temp_avg").getAsDouble();
                double tempNight = parts.getAsJsonObject("night").get("temp_avg").getAsDouble();

                // Расчет средней температуры дня
                double avgDayTemp = (tempMorning + tempDay + tempEvening + tempNight) / 4.0;

                // Сохраняем данную температуру в список
                dailyAvgTemp.add(avgDayTemp);
            }

            // Расчет средней температуры в интервале дней
            double total = 0;
            for (double dailyAvg : dailyAvgTemp) {
                total += dailyAvg;
            }
            double avgTemperature = total / dailyAvgTemp.size();

            System.out.printf("Средняя температура в период с %s по %s: %.1f °C\n", startDate, endDate, avgTemperature);

        } catch (Exception ex) {
            System.out.println("Ошибка обработки данных погоды: " + ex.getMessage());
        }
    }

    // Метод преобразования Json файла в читаемый
    public static String prettyJson(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object json = gson.fromJson(jsonString, Object.class);
        return gson.toJson(json);
    }
}