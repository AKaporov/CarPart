package ru.hw.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CountryType {
    RUSSIA("Russia");

    private final String name;
}
