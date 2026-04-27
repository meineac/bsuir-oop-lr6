package com.forecast.service;

import com.forecast.properties.CityProperties;
import com.forecast.properties.CityProperties.Coordinate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationResolverTest {

    @Test
    void resolve_ValidCity() {
        CityProperties properties = new CityProperties();
        Coordinate minskCoords = new Coordinate();
        minskCoords.setLat(new BigDecimal("53.9006"));
        minskCoords.setLon(new BigDecimal("27.5590"));
        properties.setCities(Map.of("Minsk", minskCoords));

        LocationResolver resolver = new LocationResolver(properties);

        CityProperties.Coordinate result = resolver.resolve("Minsk");

        assertEquals(new BigDecimal("53.9006"), result.getLat());
        assertEquals(new BigDecimal("27.5590"), result.getLon());
    }

    @Test
    void resolve_InvalidCity() {
        CityProperties properties = new CityProperties();
        properties.setCities(Map.of());

        LocationResolver resolver = new LocationResolver(properties);
        assertThrows(IllegalArgumentException.class, () -> resolver.resolve("UnknownCity"));
    }
}