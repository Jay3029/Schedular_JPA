package com.sparta.schedular_jpa.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {

    private String date;
    private String weather;

    public Weather(String date, String weather) {
        this.date = date;
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
