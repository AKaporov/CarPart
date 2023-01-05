package ru.hw.demo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.repository.CarPartRepository;
import ru.hw.demo.service.exception.CarPartNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Rest контроллер по работе с запчастями")
class CarPartControllerTest {
    public static final String VENDOR_CODE_NOT_VALIDATE = "VendorCode";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CarPartRepository carPartRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Transactional
    @Test
    @DisplayName("должен вернуть найденную запчасть по её vendorCode")
    void shouldReturnCarPartFoundByVendorCodeInRequestParam() throws Exception {
        CarPart cpTwo = carPartRepository.getOne(2L);  // 'URL-4320-01'

        CarPartRecommendedDto expectedResult = CarPartRecommendedDto.builder()
                .vendorCode(cpTwo.getVendorCode())
                .sku(cpTwo.getSku())
                .name(cpTwo.getName())
                .id(cpTwo.getId())
                .rating(cpTwo.getRating())
                .price(cpTwo.getPrice())
                .build();

        mvc.perform(
                        get("/api/v1/carparts")
                                .param("VendorCode", cpTwo.getVendorCode())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("должен вернуть CarPartNotFoundException, если по переданному vendorCode не была найдена запчасть")
    void shouldReturnCarPartNotFoundExceptionIfVendorCodeNotValid() throws Exception {
        mvc.perform(
                        get("/api/v1/carparts")
                                .param("VendorCode", VENDOR_CODE_NOT_VALIDATE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CarPartNotFoundException));
    }

    @Transactional
    @Test
    @DisplayName("должен возвращать запчасть по её марке и году выпуска модели. Марка и год выпуска переданы через RequestParam")
    void shouldReturnCarPartByFilterInRequestParam() throws Exception {
        CarPart cpOne = carPartRepository.getOne(1L);  // 'GZ-750Z370-S'
        CarPart cpSeven = carPartRepository.getOne(7L);  // 'GZ-511.1601130-280'

        List<CarPartRecommendedDto> expectedResult = new ArrayList<>(2);
        expectedResult.add(CarPartRecommendedDto.builder()
                .id(cpOne.getId())
                .price(cpOne.getPrice())
                .rating(cpOne.getRating())
                .name(cpOne.getName())
                .sku(cpOne.getSku())
                .vendorCode(cpOne.getVendorCode())
                .build());
        expectedResult.add(CarPartRecommendedDto.builder()
                .id(cpSeven.getId())
                .price(cpSeven.getPrice())
                .rating(cpSeven.getRating())
                .name(cpSeven.getName())
                .sku(cpSeven.getSku())
                .vendorCode(cpSeven.getVendorCode())
                .build());

        mvc.perform(
                        get("/api/v1/carparts")
                                .param("brandName", cpOne.getBrand().getName()) // Gaz
                                .param("yearRelease", cpOne.getModel().getYearRelease().toString()) // 1964
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }
}