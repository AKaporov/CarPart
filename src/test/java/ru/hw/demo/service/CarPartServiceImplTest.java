package ru.hw.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.generate.CarPartGenerate;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepository;
import ru.hw.demo.service.component.CarPartSpecification;
import ru.hw.demo.service.convert.ConvertCarPartToRecommendedDto;
import ru.hw.demo.service.exception.CarPartNotFoundException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис по работе с запчастями")
class CarPartServiceImplTest {

    public static final String VENDOR_CODE_NOT_VALIDATE = "VendorCode";

    private CarPartServiceImpl carPartService;

    @Mock
    private CarPartRepository carPartRepository;
    @Mock
    private ConvertCarPartToRecommendedDto toRecommendedDto;
    @Mock
    private CarPartSpecification carPartSpecification;

    @BeforeEach
    void setUp() {
        carPartService = new CarPartServiceImpl(carPartRepository, toRecommendedDto, carPartSpecification);
    }

    @Test
    @DisplayName("должен находить запчасть по каталожному номеру")
    void shouldFindCarPartByVendorCode() {
        CarPart carPart = CarPartGenerate.getUaz446(789L);
        when(carPartRepository.findByVendorCode(carPart.getVendorCode())).thenReturn(Optional.of(carPart));

        CarPartRecommendedDto expectedCarPart = CarPartRecommendedDto.builder()
                .vendorCode(carPart.getVendorCode())
                .sku(carPart.getSku())
                .name(carPart.getName())
                .rating(carPart.getRating())
                .price(carPart.getPrice())
                .id(carPart.getId())
                .build();
        when(toRecommendedDto.convertToRecommendedDto(List.of(carPart))).thenReturn(List.of(expectedCarPart));

        CarPartRecommendedDto actualCarPart = carPartService.getByVendorCode(carPart.getVendorCode());

        assertThat(actualCarPart).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedCarPart);
    }

    @Test
    @DisplayName("должен вернуть CarPartNotFoundException, если по переданному vendorCode не была найдена запчасть")
    void shouldReturnCarPartNotFoundExceptionIfVendorCodeNotValid(){
        when(carPartRepository.findByVendorCode(VENDOR_CODE_NOT_VALIDATE)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carPartService.getByVendorCode(VENDOR_CODE_NOT_VALIDATE)).isInstanceOf(CarPartNotFoundException.class);
    }

    @Test
    @DisplayName("должен находить запчасть по переданному предикату (марка, модель, год выпуска или двигатель)")
    void shouldFindCarPartByPredicate() {
        CarPart carPart = CarPartGenerate.getUaz446(789L);

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(carPart.getBrand().getName())
                .modelName(carPart.getModel().getName())
                .yearRelease(carPart.getModel().getYearRelease())
                .engineName(carPart.getEngine().getName())
                .build();

        Specification<CarPart> where = new Specification<CarPart>() {
            @Override
            public Predicate toPredicate(Root<CarPart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                criteriaBuilder.equal(root.get("brand"), carPart.getBrand());
                criteriaBuilder.equal(root.get("model"), carPart.getModel());
                return criteriaBuilder.equal(root.get("engine"), carPart.getEngine());
            }
        };
        when(carPartSpecification.getCarPart(filter)).thenReturn(where);

        List<CarPart> cpFoundList = List.of(carPart);
        when(carPartRepository.findAll(where)).thenReturn(cpFoundList);

        CarPartRecommendedDto expectedCarPart = CarPartRecommendedDto.builder()
                .vendorCode(carPart.getVendorCode())
                .sku(carPart.getSku())
                .name(carPart.getName())
                .rating(carPart.getRating())
                .price(carPart.getPrice())
                .id(carPart.getId())
                .build();
        when(toRecommendedDto.convertToRecommendedDto(List.of(carPart))).thenReturn(List.of(expectedCarPart));

        List<CarPartRecommendedDto> actualCarPart = carPartService.getByFilter(filter);

        assertThat(actualCarPart).isNotNull().isNotEmpty().hasSize(List.of(expectedCarPart).size())
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedCarPart));
    }
}