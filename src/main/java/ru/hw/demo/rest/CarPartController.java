package ru.hw.demo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.service.CarPartService;

import java.util.List;

/**
 * @author Artem
 * Rest API для работы с запчастями
 */

@RequiredArgsConstructor
@RestController
public class CarPartController {

    private final CarPartService carPartService;

    @GetMapping(value = "/api/v1/carparts")
    public ResponseEntity<List<CarPartRecommendedDto>> getCarPartByParams(
            @RequestParam(required = false, defaultValue = "", name = "brandName")
                    String brandName,  // Наименование марки
            @RequestParam(required = false, defaultValue = "", name = "modelName")
                    String modelName, // Наименование модели
            @RequestParam(required = false, defaultValue = "0", name = "yearRelease")
                    Integer yearRelease, // Год выпуска
            @RequestParam(required = false, defaultValue = "", name = "engineName")
                    String engineName // Наименование двигателя

    ) {
        FilterCarPart filter = FilterCarPart.builder()
                .brandName(brandName)
                .modelName(modelName)
                .yearRelease(yearRelease)
                .engineName(engineName)
                .build();

        List<CarPartRecommendedDto> result = carPartService.getByFilter(filter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
