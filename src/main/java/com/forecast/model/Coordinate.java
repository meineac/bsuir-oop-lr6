package com.forecast.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Coordinate {
    private BigDecimal lat;
    private BigDecimal lon;
}