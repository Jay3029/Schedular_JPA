package com.sparta.schedular_jpa.dto;

import com.sparta.schedular_jpa.entity.Weather;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto {

    private String date;
    private String weather;

    public WeatherDto(Weather weather) {
        this.date = weather.getDate();
        this.weather = weather.getWeather();
    }


}
