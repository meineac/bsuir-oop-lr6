package com.forecast.service;

import java.math.BigDecimal;

import com.forecast.client.ForecastDataClient;
import com.forecast.model.ForecastWeather;
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

    public ForecastWeather getForecastWeather(BigDecimal lat, BigDecimal lon, String provider) {
        var client = registry.get(provider);
        if (!(client instanceof ForecastDataClient forecastClient)) {
            throw new IllegalArgumentException("Provider " + provider + " does not support forecasting");
        }
        return forecastClient.getForecast(lat, lon);
    }
}
