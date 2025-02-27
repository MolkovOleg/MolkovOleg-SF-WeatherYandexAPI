# SF Weather Yandex API

Этот проект представляет консольное приложение, которое предоставляет информацию о погоде в выбранном городе, используя [API Yandex.Погода](https://yandex.ru/dev/weather/) и API геолокации от [Open-Meteo](https://open-meteo.com/).

---

## 🧰 Основные функции:
1. **Ввод города от пользователя**:
   - Пользователь вводит название города для получения прогноза погоды.
   - Проверка на некорректный ввод (пустые строки, несуществующие города).

2. **Получение данных геолокации**:
   - Использует API Open-Meteo для получения координат (широта и долгота) указанного города.

3. **Запрос и отображение данных погоды**:
   - Отображается текущая температура, влажность, скорость ветра и другие параметры.
   - Расчитывается средняя температура за 3 дня с выводом интервала дат.

4. **Обработка ошибок**:
   - Проверка подключения к API и обработки некорректных ответов.

---

## 🏗️ Технологии:
- **Java 11+** — Основной язык разработки.
- **HTTP Client API** — Для отправки HTTP-запросов.
- **Gson** — Для парсинга JSON-ответов.
- **Maven** — Для управления зависимостями и сборки проекта.

---

## 📦 Установка и запуск:
### Шаг 1: Клонирование репозитория
```bash
git clone https://github.com/MolkovOleg/SF-WeatherYandexAPI.git
cd SF-WeatherYandexAPI
```

---

## Настройка API-ключей
- В файле src/main/resources/config.properties замените значение yandex.api.key на ваш API-ключ Yandex.Погода:
