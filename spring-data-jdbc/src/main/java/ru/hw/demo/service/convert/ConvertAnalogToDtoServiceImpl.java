package ru.hw.demo.service.convert;

import org.springframework.stereotype.Service;
import ru.hw.demo.domain.Analog;
import ru.hw.demo.dto.AnalogDto;

@Service
public class ConvertAnalogToDtoServiceImpl implements ConvertAnalogToDtoService {
    @Override
    public AnalogDto convertToAnalogDto(Analog analog) {
        return AnalogDto.builder()
                .vendor(analog.getVendor())
                .vendorCode(analog.getCarPart().getVendorCode())
                .name(analog.getCarPart().getName())
                .price(analog.getCarPart().getPrice())
                .rating(analog.getCarPart().getRating())
                .build();
    }
}
