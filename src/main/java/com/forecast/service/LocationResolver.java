package com.forecast.service;

import com.forecast.properties.CityProperties;
import com.forecast.properties.CityProperties.Coordinate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationResolver {
    private final CityProperties properties;

    public Coordinate resolve(String city) {
        throw new NotImplementedException();
    }
}
