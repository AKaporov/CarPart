package ru.hw.demo.service.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.AnalogDto;
import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.PhotoDto;
import ru.hw.demo.repository.AnalogRepositoryDataJdbc;

import java.util.List;

/**
 * @author Artem
 * Сервис для конвертации CarPart в CarPartFullInfoDto
 */

@Service
@RequiredArgsConstructor
public class ConvertCarPartToFullInfoDtoServiceImpl implements ConvertCarPartToFullInfoDtoService {
    private final ConvertAnalogToDtoService toAnalogDtoService;
    private final ConvertPhotoToDtoService toPhotoDtoService;
    private final AnalogRepositoryDataJdbc analogRepositoryDataJdbc;

    @Override
    public CarPartFullInfoDto convertToFullInfoDto(CarPart carPart) {
        List<AnalogDto> analogDtoList = carPart.getAnalogs().stream()
                .map(analogRef -> analogRepositoryDataJdbc.findById(analogRef.getAnalogId()).get())
                .map(toAnalogDtoService::convertToAnalogDto)
                .toList();

        List<PhotoDto> photoDtoList = carPart.getPhotos().stream()
                .map(toPhotoDtoService::convertToPhotoDto)
                .toList();

        return CarPartFullInfoDto.builder()
                .id(carPart.getId())
                .brandName(carPart.getBrandQueryDsl().getName())
                .name(carPart.getName())
                .countryName(carPart.getCountryQueryDsl().getName())
                .analogDtoList(analogDtoList)
                .description(carPart.getDescription())
                .sku(carPart.getSku())
                .vendorCode(carPart.getVendorCode())
                .engineName(carPart.getEngineQueryDsl().getName())
                .price(carPart.getPrice())
                .rating(carPart.getRating())
                .manufacturer(carPart.getManufacturer())
                .modelName(carPart.getModelQueryDsl().getName())
                .photoList(photoDtoList)
                .build();
    }
}
