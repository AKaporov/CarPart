package ru.hw.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarPartVendorCodeType {
    GZ_750Z370_S("GZ-750Z370-S"),
    URL_4320_01("URL-4320-01"),
    URL_4320_02("URL-4320-02"),
    KMZ_740_51_1117010_01("KMZ-740.51-1117010-01"),
    KMZ_7403_1008021_02("KMZ-7403-1008021-02"),
    KMZ_7406_1012010_03("KMZ-7406-1012010-03"),
    GZ_511_1601130_280("GZ-511.1601130-280"),
    KMZ_740_60_1008025_20("KMZ-740.60-1008025-20"),
    KMZ_3937478_2("KMZ-3937478-2"),
    URL_4320_020("URL-4320-020");

    private final String vendorCode;
}
