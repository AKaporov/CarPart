package ru.hw.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModelType {
    GAZ_66_1964("Gaz-66", 1964),
    URAL_4320_1977("Ural-4320", 1977),
    KAMAZ_65201_2000("Kamaz 65201", 2000);

    private final String name;
    private final int yearRelease;

}
