package com.sparta.schedular_jpa;

import com.sparta.schedular_jpa.dto.WeatherDto;
import com.sparta.schedular_jpa.entity.Weather;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
public class WeatherUtil {

    private String weatherApiUrl = "https://f-api.github.io/f-api/weather.json";

    private final RestTemplate restTemplate;

    public WeatherUtil(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public WeatherDto getWeatherOfDay() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String formattedDate = LocalDate.now().format(formatter);

        Weather[] weathers = getWeatherData(weatherApiUrl);

        Weather weather = Arrays.stream(weathers)
                .filter(weatherData -> weatherData.getDate().equals(formattedDate))
                .findFirst()
                .orElse(null);
        WeatherDto weatherDto = new WeatherDto(weather);

        return weatherDto;
    }


    public Weather[] getWeatherData(String url) {
        return restTemplate.getForObject(url, Weather[].class);
    }
}
