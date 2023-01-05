package ru.hw.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepository;
import ru.hw.demo.service.component.CarPartSpecification;
import ru.hw.demo.service.convert.ConvertCarPartToFullInfoDtoService;
import ru.hw.demo.service.convert.ConvertCarPartToRecommendedDtoService;
import ru.hw.demo.service.exception.CarPartNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarPartServiceImpl implements CarPartService {

    private final CarPartRepository carPartRepository;
    private final ConvertCarPartToRecommendedDtoService toRecommendedDto;
    private final CarPartSpecification carPartSpecification;
    private final ConvertCarPartToFullInfoDtoService toFullInfoDto;

    /**
     * @param vendorCode каталожный номер запчасти
     * @return возвращает найденную запчасть по её {@code vendorCode}
     */
    @Override
    public CarPartRecommendedDto getByVendorCode(String vendorCode) {
        return carPartRepository.findByVendorCode(vendorCode)
                .map(cp -> toRecommendedDto.convertToRecommendedDto(List.of(cp)).get(0))
                .orElseThrow(() -> new CarPartNotFoundException(vendorCode));
    }

    /**
     * @param filter фильтр поиска по марке, модели, году выпуска или двигатель.
     * @return возвращает список найденных запчастей по {@code filter}
     */
    @Override
    public List<CarPartRecommendedDto> getByFilter(FilterCarPart filter) {
        if (Objects.isNull(filter)) {
            return new ArrayList<>(1);
        }

        Specification<CarPart> where = carPartSpecification.getCarPart(filter);
        List<CarPart> cpFoundList = carPartRepository.findAll(where);

        return toRecommendedDto.convertToRecommendedDto(cpFoundList);
    }

    /**
     * @param vendorCode каталожный номер запчасти
     * @return возвращает расширенную информацию по {@code vendorCode}
     */
    @Transactional(readOnly = true)
    @Override
    public CarPartFullInfoDto getExtendedInfoByVendorCode(String vendorCode) {
        return carPartRepository.findByVendorCode(vendorCode)
                .map(toFullInfoDto::convertToFullInfoDto)
                .orElseThrow(() -> new CarPartNotFoundException(vendorCode));
    }
}
