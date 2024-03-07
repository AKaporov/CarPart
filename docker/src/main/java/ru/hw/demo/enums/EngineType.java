package ru.hw.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EngineType {
    DIESEL("Diesel"),
    PETROL("Petrol"),
    TURBOCHARGED_DIESEL("Turbocharged diesel");

    private final String name;
}
