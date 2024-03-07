package ru.hw.demo.service.convert;

import org.springframework.stereotype.Service;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;

import java.util.List;

/**
 * @author Artem
 * Сервис для конвертации CarPart в CarPartToRecommendedDto
 */

@Service
public class ConvertCarPartToRecommendedDtoServiceImpl implements ConvertCarPartToRecommendedDtoService {
    @Override
    public List<CarPartRecommendedDto> convertToRecommendedDto(List<CarPart> carPartList) {
        return carPartList.stream()
                .map(this::getRecommendedDto)
                .toList();
    }

    private CarPartRecommendedDto getRecommendedDto(CarPart carPart) {
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
