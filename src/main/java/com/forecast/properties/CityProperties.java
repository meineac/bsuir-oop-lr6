package com.forecast.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class CityProperties {
    private Map<String, Coordinate> cities;

    @Getter
    @Setter
    public static class Coordinate {
        private BigDecimal lat;
        private BigDecimal lon;
    }
}
