package ru.hw.demo.service.convert;

import org.springframework.stereotype.Service;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem
 * Сервис для конвертации CarPart в CarPartToRecommendedDto
 */
@Service
public class ConvertCarPartToRecommendedDtoImpl implements ConvertCarPartToRecommendedDto {
    @Override
    public List<CarPartRecommendedDto> convertToRecommendedDto(List<CarPart> carPartList) {
        return carPartList.stream()
                .map(this::toRecommendedDto)
                .collect(Collectors.toList());
    }

    private CarPartRecommendedDto toRecommendedDto(CarPart carPart) {
        return CarPartRecommendedDto.builder()
                .id(carPart.getId())
                .name(carPart.getName())
                .price(carPart.getPrice())
                .rating(carPart.getRating())
                .sku(carPart.getSku())
                .vendorCode(carPart.getVendorCode())
                .build();
    }
}
