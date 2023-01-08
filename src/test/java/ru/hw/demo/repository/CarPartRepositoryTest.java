package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.hw.demo.domain.*;
import ru.hw.demo.generate.CarPartGenerate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName("Репозиторий по работе с Запчасти автомобиля")
class CarPartRepositoryTest {
    @Autowired
    private CarPartRepository carPartRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EngineRepository engineRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private AnalogRepository analogRepository;

    @AfterEach
    void tearDown() {
        carPartRepository.deleteAllInBatch();
        analogRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен сохранять новую запчасть")
    void shouldSaveCarPart() {
        CarPart uaz446 = CarPartGenerate.getUaz446(null);
        CarPart uaz446Saved = carPartRepository.save(uaz446);
        CarPart moskvich2141 = CarPartGenerate.getMoskvich2141(null);
        CarPart moskvich2141Saved = carPartRepository.save(moskvich2141);

        List<Analog> analogList = new ArrayList<>(2);
        analogList.add(Analog.builder()
                .carPart(uaz446Saved)
                .vendor("Тамбовская область")
                .build());
        analogList.add(Analog.builder()
                .carPart(moskvich2141Saved)
                .vendor("Кировская область")
                .build());

        List<Photo> photoList = new ArrayList<>(1);
        photoList.add(Photo.builder()
                .photoUrl("https://localhost:8080/carpart/zaz/1/#&gid=1&pid=1")
                .build());

        Optional<Country> countryRussia = countryRepository.findById(1L);
        Optional<Engine> enginePetrol = engineRepository.findById(2L);

        CarPart cp = CarPart.builder()
                .brand(Brand.builder()
                        .name("ZAZ")
                        .build())
                .model(Model.builder()
                        .name("ZAZ-965")
                        .yearRelease(1960)
                        .build())
                .country(countryRussia.orElseGet(() -> Country.builder().name("Russia").build()))
                .engine(enginePetrol.orElseGet(() -> Engine.builder().name("Petrol").build()))
                .analogList(analogList)
                .photoList(photoList)
                .rating(5.5)
                .sku("ZZ-966-6106008-01")
                .vendorCode("20221228-01")
                .price(2000)
                .manufacturer("Russia")
                .description("Состояние отличное! Снят с рабочей машины!!!")
                .name("Бак топливный заз 965")
                .build();

        CarPart actualCarPart = carPartRepository.save(cp);

        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart.getId()).isPositive();
            assertThat(actualCarPart.getPhotoList().size()).isPositive();
            assertThat(actualCarPart.getAnalogList().size()).isPositive();
        });
    }

    @Test
    @DisplayName("должен находить запчасть по её Идентификатору")
    void shouldFindCarPartById() {
        Optional<CarPart> actualCarPart = carPartRepository.findById(2L);
        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart).isNotEmpty();
            assertThat(actualCarPart.get().getId()).isPositive();
            assertThat(actualCarPart.get().getPhotoList()).isNotEmpty().hasSizeGreaterThan(0);
            assertThat(actualCarPart.get().getAnalogList()).isNotEmpty().hasSizeGreaterThan(0);
        });
    }

    @Test
    @DisplayName("должен находить запчасть по каталожному номеру")
    void shouldFindCarPartByVendorCode() {
        Optional<CarPart> expected = carPartRepository.findById(3L);

        Optional<CarPart> actual = carPartRepository.findByVendorCode("URL-4320-02");

        assertThat(actual).isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected.get());
    }
}