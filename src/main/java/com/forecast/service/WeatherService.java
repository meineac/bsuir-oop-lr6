package com.forecast.service;

import java.math.BigDecimal;
import java.util.List;

import com.forecast.client.ForecastDataClient;
import com.forecast.client.WeatherDataClient;
import com.forecast.model.Coordinate;
import com.forecast.model.ForecastWeather;
import org.springframework.stereotype.Service;

import com.forecast.model.CurrentWeather;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClientRegistry registry;

    public CurrentWeather getCurrentWeather(BigDecimal lat, BigDecimal lon, String provider) {
        var client = registry.get(provider);
        if (!(client instanceof WeatherDataClient weatherDataClient)) {
            throw new IllegalArgumentException("Provider " + provider + " does not support current weather retrieval");
        }
        return new CurrentWeather(weatherDataClient.getCurrentTemperature(lat, lon));
    }

    public ForecastWeather getForecastWeather(BigDecimal lat, BigDecimal lon, String provider) {
        var client = registry.get(provider);
        if (!(client instanceof ForecastDataClient forecastClient)) {
            throw new IllegalArgumentException("Provider " + provider + " does not support forecasting");
        }
        return forecastClient.getForecast(lat, lon);
    }

    public List<CurrentWeather> getCurrentWeatherBatch(List<Coordinate> coordinates, String provider) {
        var client = registry.get(provider);
        if (!(client instanceof WeatherDataClient weatherDataClient)) {
            throw new IllegalArgumentException("Provider " + provider + " does not support current weather retrieval");
        }

        return coordinates.parallelStream()
                .map(coord -> new CurrentWeather(weatherDataClient.getCurrentTemperature(coord.getLat(), coord.getLon())))
                .toList();
    }
}
