package com.forecast.client;

import java.math.BigDecimal;

public interface WeatherDataClient extends WeatherProvider {
    BigDecimal getCurrentTemperature(BigDecimal lat, BigDecimal lon);
}
