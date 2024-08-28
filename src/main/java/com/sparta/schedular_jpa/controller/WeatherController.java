package com.sparta.schedular_jpa.controller;

import com.sparta.schedular_jpa.dto.WeatherDto;
import com.sparta.schedular_jpa.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherDto> getWeather(@RequestParam("date") String date) {
        WeatherDto weatherDto = weatherService.getWeatherOfDay(date);
        return ResponseEntity.ok().body(weatherDto);
    }



}
