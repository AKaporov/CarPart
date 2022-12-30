package ru.hw.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepository;
import ru.hw.demo.service.component.CarPartSpecification;
import ru.hw.demo.service.convert.ConvertCarPartToRecommendedDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarPartServiceImpl implements CarPartService {

    private final CarPartRepository carPartRepository;
    private final ConvertCarPartToRecommendedDto toRecommendedDto;
    private final CarPartSpecification carPartSpecification;

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденную запчасть по её {@code vendorCode}, иначе Optional.empty()
     */
    @Override
    public Optional<CarPartRecommendedDto> getByVendorCode(String vendorCode) {
        return carPartRepository.findByVendorCode(vendorCode)
                .map(cp -> toRecommendedDto.convertToRecommendedDto(List.of(cp)).get(0));
    }

    /**
     * @param filter фильтр поиска по марке, модели, году выпуска или двигатель.
     * @return возвращает список найденных запчастей по {@code filter}
     */
    @Override
    public List<CarPartRecommendedDto> getByFilter(FilterCarPart filter) {
        if (filter == null) {
            return new ArrayList<>(1);
        }

        Specification<CarPart> where = carPartSpecification.getCarPart(filter);
        List<CarPart> cpFoundList = carPartRepository.findAll(where);

        return toRecommendedDto.convertToRecommendedDto(cpFoundList);
    }

    /**
     * @param cpPreviewDto предварительный просмотр запчасти
     * @return возвращает дополнительную информацию по {@code cpPreviewDto}
     */
    @Override
    public Optional<CarPartFullInfoDto> getAddInfoById(CarPartRecommendedDto cpPreviewDto) {
        return Optional.empty();
    }
}
