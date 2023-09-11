package ru.hw.demo.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.service.CarPartService;
import ru.hw.demo.service.exception.CarPartNotFoundException;

import java.util.List;

/**
 * @author Artem
 * Rest API для работы с запчастями
 */

@RequiredArgsConstructor
@RestController
public class CarPartController {

    private final CarPartService carPartService;

    @GetMapping(value = "/api/v1/carparts", params = "VendorCode")
    public ResponseEntity<CarPartRecommendedDto> getCarPartRecommendedByVendorCode(
            @RequestParam(value = "VendorCode", required = true, defaultValue = "")
            String vendorCode) {

        CarPartRecommendedDto cpFound = carPartService.getByVendorCode(vendorCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cpFound);
    }

    @GetMapping(value = "/api/v1/carparts")
    public ResponseEntity<List<CarPartRecommendedDto>> getCarPartByParams(
            @RequestParam(required = false, defaultValue = "", value = "brandName") String brandName,  // Наименование марки
            @RequestParam(required = false, defaultValue = "", value = "modelName") String modelName, // Наименование модели
            @RequestParam(required = false, defaultValue = "0", value = "yearRelease") Integer yearRelease, // Год выпуска
            @RequestParam(required = false, defaultValue = "", value = "engineName") String engineName // Наименование двигателя
    ) {
        FilterCarPart filter = FilterCarPart.builder()
                .brandName(brandName)
                .modelName(modelName)
                .yearRelease(yearRelease)
                .engineName(engineName)
                .build();

        List<CarPartRecommendedDto> resultPage = carPartService.getByFilter(filter);

        return ResponseEntity
                .ok()
                .body(resultPage);
    }

    @ExceptionHandler(value = {CarPartNotFoundException.class})
    private ResponseEntity<String> handleCarPartNotFoundException(CarPartNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(String.format("The CarPart with VendorCode = {%s} was not found. Check the request details.", e.getMessage()));
    }

    @GetMapping(value = "/api/v1/carparts/{VendorCode}")
    public ResponseEntity<CarPartFullInfoDto> getExtendedInfoByVendorCode(
            @PathVariable(value = "VendorCode", required = true)
            String vendorCode) {

        CarPartFullInfoDto cpFound = carPartService.getExtendedInfoByVendorCode(vendorCode);

        return ResponseEntity
                .ok(cpFound);
    }
}
