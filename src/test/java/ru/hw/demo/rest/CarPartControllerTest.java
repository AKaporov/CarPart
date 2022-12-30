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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Rest контроллер по работе с запчастями")
class CarPartControllerTest {
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

    @Test
    @Transactional
    @DisplayName("должен возвращать запчасть по её марке и году выпуска модели. Марка и год выпуска переданы через RequestParam")
    void shouldReturnCarPartByFilterInRequestParam() throws Exception {
        CarPart cpOne = carPartRepository.getOne(1L);  // 'GZ-750Z370-S'
        CarPart cpTwo = carPartRepository.getOne(7L);  // 'GZ-511.1601130-280'

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
                .id(cpTwo.getId())
                .price(cpTwo.getPrice())
                .rating(cpTwo.getRating())
                .name(cpTwo.getName())
                .sku(cpTwo.getSku())
                .vendorCode(cpTwo.getVendorCode())
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