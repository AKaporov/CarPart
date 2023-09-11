package ru.hw.demo.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.example.sql.QEngines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.hw.demo.constant.MainConstant;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.generate.CarPartGenerate;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepositoryDataJdbc;
import ru.hw.demo.repository.CustomCarPartRepositoryDataJdbc;
import ru.hw.demo.service.component.CarPartSpecification;
import ru.hw.demo.service.convert.ConvertCarPartToFullInfoDtoService;
import ru.hw.demo.service.convert.ConvertCarPartToRecommendedDtoService;
import ru.hw.demo.service.exception.CarPartNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.querydsl.example.sql.QBrands.brands;
import static com.querydsl.example.sql.QModels.models;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис по работе с запчастями")
class CarPartServiceImplTest {

    public static final String VENDOR_CODE_NOT_VALIDATE = "VendorCode";
    private static final Pageable SORT_BY_VENDOR_CODE_AND_ID = PageRequest.of(0, MainConstant.PAGE_REQUEST_SIZE, Sort.by("vendorCode", "id"));

    private CarPartServiceImpl carPartService;

    @Mock
    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;
    @Mock
    private ConvertCarPartToRecommendedDtoService toRecommendedDto;
    @Mock
    private CarPartSpecification carPartSpecification;
    @Mock
    private ConvertCarPartToFullInfoDtoService toFullInfoDto;
    @Mock
    private CustomCarPartRepositoryDataJdbc customCarPartRepositoryDataJdbc;

    @BeforeEach
    void setUp() {
        carPartService = new CarPartServiceImpl(carPartRepositoryDataJdbc, toRecommendedDto, carPartSpecification,
                toFullInfoDto, customCarPartRepositoryDataJdbc);
    }

    @Test
    @DisplayName("должен находить запчасть по каталожному номеру")
    void shouldFindCarPartByVendorCode() {
        CarPart carPart = CarPartGenerate.getUaz446(789L);
        when(carPartRepositoryDataJdbc.findByVendorCode(carPart.getVendorCode())).thenReturn(Optional.of(carPart));

        CarPartRecommendedDto expectedCarPart = CarPartRecommendedDto.builder()
                .vendorCode(carPart.getVendorCode())
                .sku(carPart.getSku())
                .name(carPart.getName())
                .rating(carPart.getRating())
                .price(carPart.getPrice())
                .id(carPart.getId())
                .build();
        when(toRecommendedDto.convertToRecommendedDto(carPart)).thenReturn(expectedCarPart);

        CarPartRecommendedDto actualCarPart = carPartService.getByVendorCode(carPart.getVendorCode());

        assertThat(actualCarPart).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedCarPart);
    }

    @Test
    @DisplayName("должен вернуть CarPartNotFoundException, если по переданному vendorCode не была найдена запчасть")
    void shouldReturnCarPartNotFoundExceptionIfVendorCodeNotValid() {
        when(carPartRepositoryDataJdbc.findByVendorCode(VENDOR_CODE_NOT_VALIDATE)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carPartService.getByVendorCode(VENDOR_CODE_NOT_VALIDATE)).isInstanceOf(CarPartNotFoundException.class);
    }

    @Test
    @DisplayName("должен находить запчасть по переданному предикату (марка, модель, год выпуска или двигатель)")
    void shouldFindCarPartByPredicate() {
        CarPart carPart = CarPartGenerate.getUaz446(789L);

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(carPart.getName())
                .modelName(carPart.getDescription())
                .engineName(carPart.getManufacturer())
                .build();

        Predicate where = brands.name.eq(filter.getBrandName())
                .and(models.name.eq(filter.getModelName()))
                .and(QEngines.engines.name.eq(filter.getEngineName()));
        when(carPartSpecification.getPredicateByFilter(filter)).thenReturn(where);

        List<CarPart> cpFoundPages = List.of(carPart);
        when(customCarPartRepositoryDataJdbc.findAll(where)).thenReturn(cpFoundPages);

        CarPartRecommendedDto expectedCarPart = CarPartRecommendedDto.builder()
                .vendorCode(carPart.getVendorCode())
                .sku(carPart.getSku())
                .name(carPart.getName())
                .rating(carPart.getRating())
                .price(carPart.getPrice())
                .id(carPart.getId())
                .build();
        when(toRecommendedDto.convertToRecommendedDto(carPart)).thenReturn(expectedCarPart);

        List<CarPartRecommendedDto> actualCarPart = carPartService.getByFilter(filter);

        assertThat(actualCarPart).isNotNull().isNotEmpty().hasSize(List.of(expectedCarPart).size())
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedCarPart));
    }

    @Test
    @DisplayName("должен вернуть пустой список, если переданный filter = null")
    void shouldReturnEmptyListIfFilterIsNull() {
        List<CarPartRecommendedDto> actualList = carPartService.getByFilter(null);

        assertThat(actualList).isNotNull().isEmpty();
    }
}