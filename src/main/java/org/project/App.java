package org.project;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String city;

        do {
            // Получение пользовательских данных
            System.out.println("========================================================");
            System.out.print("Введите город (Введите Exit для выхода): ");
            city = scanner.nextLine();

            // Реализация выхода из приложения
            if (city.equalsIgnoreCase("Exit")) break;

            // Проверка на пустое поле города
            if (city.trim().isEmpty()) {
                System.out.println("Название города не может быть пустым!");
                continue;
            }

            try {
                // Получение данных геолокации и проверка
                var cityLocationData = GeolocationService.getLocationData(city);
                if (cityLocationData == null) {
                    System.out.println("Не удалось найти данные города: " + city);
                    continue;
                }

                // Получение и отображение данных погоды
                WeatherService.displayWeatherData(
                        cityLocationData.get("latitude").getAsDouble(),
                        cityLocationData.get("longitude").getAsDouble());

            } catch (Exception ex) {
                System.out.println("Произошла ошибка: " + ex.getMessage());
            }
        } while (!city.equalsIgnoreCase("Exit"));
    }
}