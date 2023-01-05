package ru.hw.demo.service.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hw.demo.domain.Analog;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.AnalogDto;
import ru.hw.demo.repository.CarPartRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConvertAnalogToDtoServiceImpl implements ConvertAnalogToDtoService {
    private final CarPartRepository carPartRepository;

    @Override
    public AnalogDto convertToAnalogDto(Analog analog) {
        Optional<CarPart> foundAnalog = carPartRepository.findById(analog.getCarPartId());

        return AnalogDto.builder()
                .vendor(analog.getVendor())
                .vendorCode(foundAnalog.map(CarPart::getVendorCode).orElse(""))
                .name(foundAnalog.map(CarPart::getName).orElse(""))
                .price(foundAnalog.map(CarPart::getPrice).orElse(0.0))
                .rating(foundAnalog.map(CarPart::getRating).orElse(0.0))
                .build();
    }
}
