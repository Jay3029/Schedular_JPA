package com.sparta.schedular_jpa.service;

import com.sparta.schedular_jpa.dto.WeatherDto;
import com.sparta.schedular_jpa.entity.Weather;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Service
public class WeatherService {

    private String weatherApiUrl = "https://f-api.github.io/f-api/weather.json";

    private final RestTemplate restTemplate;


    public WeatherService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public WeatherDto getWeatherOfDay(String date) {

        Weather[] weathers = getWeatherData(weatherApiUrl);

        Weather weather = Arrays.stream(weathers)
                .filter(weatherData -> weatherData.getDate().equals(date))
                .findFirst()
                .orElse(null);
        WeatherDto weatherDto = new WeatherDto(weather);

        return weatherDto;
    }


    public Weather[] getWeatherData(String url) {
        return restTemplate.getForObject(url, Weather[].class);
    }

}


    // 바보같은 실수 췤
    /*private final RestTemplate restTemplate;

    // 하루 날씨
    public WeatherDto getWeather(String date) {
        try {

            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:7070")
                    .path("/api/weather")
                    .queryParam("date",date)
                    .encode()
                    .build()
                    .toUri();

            System.out.println("Request URI: " + uri);

            ResponseEntity<WeatherDto> responseEntity = restTemplate.getForEntity(uri, WeatherDto.class);

            return responseEntity.getBody();

        } catch (Exception e) {
            throw new RuntimeException("뭔지 모르지만 에러란다 친구야..", e);
        }

    }*/

