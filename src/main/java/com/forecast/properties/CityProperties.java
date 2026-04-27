package com.forecast.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Component
@ConfigurationProperties(prefix = "app")
public class CityProperties {
    private final Map<String, Coordinate> cities = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    @Getter
    @Setter
    public static class Coordinate {
        private BigDecimal lat;
        private BigDecimal lon;
    }
}
