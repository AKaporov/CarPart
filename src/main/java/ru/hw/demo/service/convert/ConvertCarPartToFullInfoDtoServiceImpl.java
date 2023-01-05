package ru.hw.demo.service.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.domain.Photo;
import ru.hw.demo.dto.AnalogDto;
import ru.hw.demo.dto.CarPartFullInfoDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem
 * Сервис для конвертации CarPart в CarPartFullInfoDto
 */

@Service
@RequiredArgsConstructor
public class ConvertCarPartToFullInfoDtoServiceImpl implements ConvertCarPartToFullInfoDtoService {
    private final ConvertAnalogToDtoService toAnalogDtoService;

    @Override
    public CarPartFullInfoDto convertToFullInfoDto(CarPart carPart) {
        List<AnalogDto> analogDtoList = carPart.getAnalogList().stream()
                .map(toAnalogDtoService::convertToAnalogDto)
                .collect(Collectors.toList());

        List<String> photoList = carPart.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());

        return CarPartFullInfoDto.builder()
                .id(carPart.getId())
                .brandName(carPart.getBrand().getName())
                .name(carPart.getName())
                .countryName(carPart.getCountry().getName())
                .analogDtoList(analogDtoList)
                .description(carPart.getDescription())
                .sku(carPart.getSku())
                .vendorCode(carPart.getVendorCode())
                .engineName(carPart.getEngine().getName())
                .price(carPart.getPrice())
                .rating(carPart.getRating())
                .manufacturer(carPart.getManufacturer())
                .modelName(carPart.getModel().getName())
                .photoList(photoList)
                .build();
    }
}
