package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hw.demo.domain.*;
import ru.hw.demo.generate.CarPartGenerate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJdbcTest
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с объектом Запчасти автомобиля")
class CarPartRepositoryDataJdbcTest {
    @Autowired
    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;
    @Autowired
    private BrandRepositoryDataJdbc brandRepositoryDataJdbc;
    @Autowired
    private CountryRepositoryDataJdbc countryRepositoryDataJdbc;
    @Autowired
    private EngineRepositoryDataJdbc engineRepositoryDataJdbc;
    @Autowired
    private ModelRepositoryDataJdbc modelRepositoryDataJdbc;
    @Autowired
    private AnalogRepositoryDataJdbc analogRepositoryDataJdbc;
    @Autowired
    private PhotoRepositoryDataJdbc photoRepositoryDataJdbc;

    @AfterEach
    void tearDown() {
        carPartRepositoryDataJdbc.deleteAll();
        analogRepositoryDataJdbc.deleteAll();
    }

    @Test
    @DisplayName("должен сохранять новую запчасть")
    void shouldSaveCarPart() {
        CarPart uaz446Saved = createUaz446();
        CarPart moskvich2141Saved = createMoskvich2141();

        List<Analog> analogList = new ArrayList<>(2);
        analogList.add(Analog.builder()
                .carPart(uaz446Saved)
                .vendor("Тамбовская область")
                .build());
        analogList.add(Analog.builder()
                .carPart(moskvich2141Saved)
                .vendor("Кировская область")
                .build());
        List<Analog> analogListSaved = analogRepositoryDataJdbc.saveAll(analogList);

        Set<Photo> photoSet = new HashSet<>(1);
        photoSet.add(Photo.builder()
                .photoUrl("https://localhost:8080/carpart/zaz/1/#&gid=1&pid=1")
                .build());

        Country countryRussia = countryRepositoryDataJdbc.findById(1L).orElseGet(() -> countryRepositoryDataJdbc.save(Country.builder().name("Russia").build()));
        Engine enginePetrol = engineRepositoryDataJdbc.findById(2L).orElseGet(() -> engineRepositoryDataJdbc.save(Engine.builder().name("Petrol").build()));

        Brand brandZaz = brandRepositoryDataJdbc.insert(Brand.builder()
                .name("ZAZ")
                .build());

        Model modelZaz965 = modelRepositoryDataJdbc.save(Model.builder()
                .name("ZAZ-965")
                .yearRelease(1960)
                .build());

        Set<AnalogRef> analogRefList = new HashSet<>(analogListSaved.size());
        analogListSaved.forEach(analog -> analogRefList.add(AnalogRef.builder()
                .analogId(analog.getId())
                .build()));

        CarPart cp = CarPart.builder()
                .brandRef(brandZaz::getId)
                .modelRef(modelZaz965::getId)
                .countryRef(countryRussia::getId)
                .engineRef(enginePetrol::getId)
                .photos(photoSet)
                .analogs(analogRefList)
                .rating(5.5d)
                .sku("ZZ-966-6106008-01")
                .vendorCode("20221228-01")
                .price(2000d)
                .manufacturer("Russia")
                .description("Состояние отличное! Снят с рабочей машины!!!")
                .name("Бак топливный заз 965")
                .build();

        CarPart actualCarPart = carPartRepositoryDataJdbc.save(cp);

        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart.getId()).isPositive();
            assertThat(actualCarPart.getPhotos()).isNotEmpty().hasSize(1);
            assertThat(actualCarPart.getAnalogs()).isNotEmpty().hasSize(2);
        });
    }

    private CarPart createMoskvich2141() {
        Brand brand = brandRepositoryDataJdbc.insert(Brand.builder().name("Moskvich").build());
        Model model = modelRepositoryDataJdbc.save(Model.builder().name("Moskvich 2141").yearRelease(2_000).build());
        Engine engine = engineRepositoryDataJdbc.save(Engine.builder().name("Petrol Turbocharged").build());
        Country country = countryRepositoryDataJdbc.save(Country.builder().name("Lithuania").build());

        CarPart moskvich2141 = CarPartGenerate.getMoskvich2141(null);

        moskvich2141.setBrandRef(() -> brand.getId());
        moskvich2141.setModelRef(model::getId);
        moskvich2141.setEngineRef(engine::getId);
        moskvich2141.setCountryRef(country::getId);

        return carPartRepositoryDataJdbc.save(moskvich2141);
    }

    private CarPart createUaz446() {
        Brand brand = brandRepositoryDataJdbc.insert(Brand.builder().name("UAZ").build());
        Model model = modelRepositoryDataJdbc.save(Model.builder().name("UAZ-469").yearRelease(1972).build());
        Engine engine = engineRepositoryDataJdbc.save(Engine.builder().name("Turbocharged petrol").build());
        Country country = countryRepositoryDataJdbc.save(Country.builder().name("Morocco").build());

        CarPart uaz446 = CarPartGenerate.getUaz446(null);

        uaz446.setBrandRef(() -> brand.getId());
        uaz446.setModelRef(model::getId);
        uaz446.setEngineRef(engine::getId);
        uaz446.setCountryRef(country::getId);

        return carPartRepositoryDataJdbc.save(uaz446);
    }

    @Test
    @DisplayName("должен находить запчасть по её Идентификатору")
    void shouldFindCarPartById() {
        Optional<CarPart> actualCarPart = carPartRepositoryDataJdbc.findById(2L);
        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart).isNotEmpty();
            assertThat(actualCarPart.get().getId()).isPositive();
            assertThat(actualCarPart.get().getPhotos()).isNotEmpty().hasSize(2);
            assertThat(actualCarPart.get().getAnalogs()).isNotEmpty().hasSize(2);
        });
    }

    @Test
    @DisplayName("должен находить запчасть по каталожному номеру")
    void shouldFindCarPartByVendorCode() {
        Optional<CarPart> expected = carPartRepositoryDataJdbc.findById(3L);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        Optional<CarPart> actual = carPartRepositoryDataJdbc.findByVendorCode("URL-4320-02");
        assertAll(() -> {
            assertThat(actual).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .ignoringFields("photos", "analogs")
                    .isEqualTo(expected.get());

            assertThat(actual.get().getPhotos()).isNotEmpty()
                    .containsExactlyInAnyOrderElementsOf(expected.get().getPhotos());
            assertThat(actual.get().getAnalogs()).isNotEmpty()
                    .containsExactlyInAnyOrderElementsOf(expected.get().getAnalogs());

        });
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    }

    @Test
    @DisplayName("не должен находить запчасть по не корректному наименованию")
    void shouldNotFoundBrandByNotValidName() {
        Optional<CarPart> actualCartPart = carPartRepositoryDataJdbc.findById(101L);

        assertThat(actualCartPart).isNotPresent();
    }

//    @Test
//    @DisplayName("должен находить все автозапчасти по переданному списку марок")
//    void shouldFindAllCarPartByBrandsId() {
//        Brand ural = brandRepositoryDataJdbc.findByName("Ural").get();
//        Brand kamaz = brandRepositoryDataJdbc.findByName("Kamaz").get();
//
//        Collection<CarPart> actualCarParts = carPartRepositoryDataJdbc.findAllByBrandRefIn(List.of(kamaz.getId(), ural.getId()));
//
//        // brand Kamaz
//        CarPart cp4 = carPartRepositoryDataJdbc.findById(4L).get();
//        CarPart cp5 = carPartRepositoryDataJdbc.findById(5L).get();
//        CarPart cp6 = carPartRepositoryDataJdbc.findById(6L).get();
//        CarPart cp8 = carPartRepositoryDataJdbc.findById(8L).get();
//        CarPart cp9 = carPartRepositoryDataJdbc.findById(9L).get();
//
//        // brand Ural
//        CarPart cp2 = carPartRepositoryDataJdbc.findById(2L).get();
//        CarPart cp3 = carPartRepositoryDataJdbc.findById(3L).get();
//        CarPart cp10 = carPartRepositoryDataJdbc.findById(10L).get();
//
//        Set<CarPart> expectedCarParts = new HashSet<>(8);
//        expectedCarParts.add(cp4);
//        expectedCarParts.add(cp5);
//        expectedCarParts.add(cp6);
//        expectedCarParts.add(cp8);
//        expectedCarParts.add(cp9);
//        expectedCarParts.add(cp2);
//        expectedCarParts.add(cp3);
//        expectedCarParts.add(cp10);
//
//        assertThat(actualCarParts).isNotEmpty()
//                .containsExactlyInAnyOrderElementsOf(expectedCarParts);
//
//    }

//    @Test
//    @DisplayName("должен находить все автозапчасти по переданному списку идентификаторов моделей")
//    void shouldFindAllCarPartByModelId() {
//        Long gaz66Id = 1L;
//        Long kamazId = 3L;
//        Collection<CarPart> actualCarParts = carPartRepositoryDataJdbc.findAllByModelRefIn(List.of(gaz66Id, kamazId));
//
//        // Gaz-66
//        CarPart cp1 = carPartRepositoryDataJdbc.findById(1L).get();
//        CarPart cp7 = carPartRepositoryDataJdbc.findById(7L).get();
//
//        // Kamaz 65201
//        CarPart cp4 = carPartRepositoryDataJdbc.findById(4L).get();
//        CarPart cp5 = carPartRepositoryDataJdbc.findById(5L).get();
//        CarPart cp6 = carPartRepositoryDataJdbc.findById(6L).get();
//        CarPart cp8 = carPartRepositoryDataJdbc.findById(8L).get();
//        CarPart cp9 = carPartRepositoryDataJdbc.findById(9L).get();
//
//        Set<CarPart> expectedCarParts = new HashSet<>(7);
//        expectedCarParts.add(cp9);
//        expectedCarParts.add(cp4);
//        expectedCarParts.add(cp1);
//        expectedCarParts.add(cp6);
//        expectedCarParts.add(cp8);
//        expectedCarParts.add(cp7);
//        expectedCarParts.add(cp5);
//
//        assertThat(actualCarParts).isNotEmpty()
//                .containsExactlyInAnyOrderElementsOf(expectedCarParts);
//    }

//    @Test
//    @DisplayName("должен находить все автозапчасти по переданному списку идентификаторов двигателей")
//    void shouldFinaAllAcrPartByEngineId() {
//        Long dieselId = 1L;
//        Long turboDieselId = 3L;
//
//        Collection<CarPart> actualCarParts = carPartRepositoryDataJdbc.findAllByEngineRefIn(Set.of(dieselId, turboDieselId));
//
//        // Diesel
//        CarPart cp1 = carPartRepositoryDataJdbc.findById(1L).get();
//        CarPart cp2 = carPartRepositoryDataJdbc.findById(2L).get();
//        CarPart cp3 = carPartRepositoryDataJdbc.findById(3L).get();
//        CarPart cp10 = carPartRepositoryDataJdbc.findById(10L).get();
//
//
//        // Turbocharged diesel
//        CarPart cp4 = carPartRepositoryDataJdbc.findById(4L).get();
//        CarPart cp5 = carPartRepositoryDataJdbc.findById(5L).get();
//        CarPart cp6 = carPartRepositoryDataJdbc.findById(6L).get();
//        CarPart cp8 = carPartRepositoryDataJdbc.findById(8L).get();
//        CarPart cp9 = carPartRepositoryDataJdbc.findById(9L).get();
//
//        Set<CarPart> expectedCarParts = new HashSet<>(9);
//        expectedCarParts.add(cp1);
//        expectedCarParts.add(cp2);
//        expectedCarParts.add(cp3);
//        expectedCarParts.add(cp4);
//        expectedCarParts.add(cp5);
//        expectedCarParts.add(cp6);
//        expectedCarParts.add(cp8);
//        expectedCarParts.add(cp9);
//        expectedCarParts.add(cp10);
//
//        assertThat(actualCarParts).isNotEmpty()
//                .containsExactlyInAnyOrderElementsOf(expectedCarParts);
//    }
}