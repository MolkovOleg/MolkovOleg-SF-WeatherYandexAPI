package org.project;

import java.io.IOException;
import java.util.Properties;

public class Config {
    public static final String YANDEX_API_KEY;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
            YANDEX_API_KEY = properties.getProperty("yandex.api.key");

            if (YANDEX_API_KEY == null || YANDEX_API_KEY.isEmpty()) {
                throw new RuntimeException("API ключ не найден в файле config.properties");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка загрузки config.properties", ex);
        }
    }
}