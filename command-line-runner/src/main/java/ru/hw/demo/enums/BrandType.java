package ru.hw.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BrandType {
    GAZ("Gaz"),
    URAL("Ural"),
    KAMAZ("Kamaz");

    private final String name;
}
