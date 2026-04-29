package com.forecast.service;

import com.forecast.client.WeatherProvider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeatherClientRegistry {
    private final Map<String, WeatherProvider> clients;

    public WeatherClientRegistry(List<WeatherProvider> clients) {
        this.clients = clients.stream()
                .collect(Collectors.toMap(
                        WeatherProvider::getProviderName,
                        Function.identity(),
                        (a, _) -> {
                            throw new IllegalArgumentException("Duplicate provider name: " + a.getProviderName());
                        }
                ));
    }

    public WeatherProvider get(String provider) {
        if (provider == null || !clients.containsKey(provider)) {
            throw new IllegalArgumentException("Unknown provider: " + provider);
        }
        return clients.get(provider);
    }
}
