package ru.hw.demo.service.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.generate.CarPartGenerate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис для конвертации CarPart в CarPartToRecommendedDto")
class ConvertCarPartToRecommendedDtoServiceImplTest {
    private ConvertCarPartToRecommendedDtoServiceImpl toRecommendedDto;

    @BeforeEach
    void setUp() {
        toRecommendedDto = new ConvertCarPartToRecommendedDtoServiceImpl();
    }

    @Test
    @DisplayName("должен корректно конвертировать")
    void shouldConvert() {
        CarPart carPart = CarPartGenerate.getMoskvich2141(777L);

        CarPartRecommendedDto actual = toRecommendedDto.convertToRecommendedDto(carPart);

        CarPartRecommendedDto expected = CarPartRecommendedDto.builder()
                .vendorCode(carPart.getVendorCode())
                .sku(carPart.getSku())
                .name(carPart.getName())
                .rating(carPart.getRating())
                .price(carPart.getPrice())
                .id(carPart.getId())
                .build();

        assertEquals(expected, actual);
    }
}