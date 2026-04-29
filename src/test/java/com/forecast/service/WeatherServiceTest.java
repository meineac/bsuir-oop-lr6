package com.forecast.service;

import com.forecast.client.ForecastDataClient;
import com.forecast.client.WeatherDataClient;
import com.forecast.model.CurrentWeather;
import com.forecast.model.ForecastWeather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherDataClient currentWeatherClient;

    @Mock
    private ForecastDataClient forecastDataClient;

    @Mock
    private WeatherClientRegistry registry;

    @InjectMocks
    private WeatherService weatherService;

    private static final String PROVIDER = "openweather";

    @Test
    void getCurrentWeather_ReturnsCorrectData() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");
        BigDecimal expectedTemp = new BigDecimal("15.5");

        when(registry.get(PROVIDER)).thenReturn(currentWeatherClient);
        when(currentWeatherClient.getCurrentTemperature(lat, lon)).thenReturn(expectedTemp);

        CurrentWeather result = weatherService.getCurrentWeather(lat, lon, PROVIDER);

        assertEquals(expectedTemp, result.getTemperature());
        verify(registry).get(PROVIDER);
        verify(currentWeatherClient).getCurrentTemperature(lat, lon);
    }

    @Test
    void getCurrentWeather_PropagatesClientException() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        when(registry.get(PROVIDER)).thenReturn(currentWeatherClient);
        when(currentWeatherClient.getCurrentTemperature(lat, lon)).thenThrow(new RuntimeException("API error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                weatherService.getCurrentWeather(lat, lon, PROVIDER));

        assertEquals("API error", exception.getMessage());
        verify(registry).get(PROVIDER);
        verify(currentWeatherClient).getCurrentTemperature(lat, lon);
    }

    @Test
    void getForecastWeather_ReturnsCorrectData() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        ForecastWeather expectedForecast = new ForecastWeather(List.of(
                new ForecastWeather.DailyForecast(
                        LocalDate.now(),
                        new BigDecimal("10.0"),
                        new BigDecimal("15.0")
                )
        ));

        WeatherDataClient hybridClient = org.mockito.Mockito.mock(
                WeatherDataClient.class,
                org.mockito.Mockito.withSettings().extraInterfaces(ForecastDataClient.class)
        );

        when(registry.get(PROVIDER)).thenReturn(hybridClient);
        when(((ForecastDataClient) hybridClient).getForecast(lat, lon)).thenReturn(expectedForecast);

        ForecastWeather result = weatherService.getForecastWeather(lat, lon, PROVIDER);

        assertEquals(expectedForecast.getDays().size(), result.getDays().size());
        verify(registry).get(PROVIDER);
        verify((ForecastDataClient) hybridClient).getForecast(lat, lon);
    }

    @Test
    void getForecastWeather_UnsupportedProvider_ThrowsException() {
        BigDecimal lat = new BigDecimal("53.9006");
        BigDecimal lon = new BigDecimal("27.5590");

        when(registry.get(PROVIDER)).thenReturn(currentWeatherClient); // client mock only implements WeatherDataClient

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                weatherService.getForecastWeather(lat, lon, PROVIDER));

        assertEquals("Provider openweather does not support forecasting", exception.getMessage());
    }
}
