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
    private Map<String, Coordinate> cities;

    public void setCities(Map<String, Coordinate> cities) {
        this.cities = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (cities != null) {
            this.cities.putAll(cities);
        }
    }

    @Getter
    @Setter
    public static class Coordinate {
        private BigDecimal lat;
        private BigDecimal lon;
    }
}
