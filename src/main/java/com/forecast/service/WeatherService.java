package com.forecast.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.forecast.model.CurrentWeather;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClientRegistry registry;

    public CurrentWeather getCurrentWeather(BigDecimal lat, BigDecimal lon, String provider) {
        BigDecimal temperature = registry.get(provider).getCurrentTemperature(lat, lon);
        return new CurrentWeather(temperature);
    }
}
