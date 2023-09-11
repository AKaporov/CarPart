package ru.hw.demo.service.convert;

import org.springframework.stereotype.Service;
import ru.hw.demo.domain.Photo;
import ru.hw.demo.dto.PhotoDto;

/**
 * @author Artem
 * Сервис для конвертации Photo в PhotoDto
 */

@Service
public class ConvertPhotoToDtoServiceImpl implements ConvertPhotoToDtoService {
    @Override
    public PhotoDto convertToPhotoDto(Photo photo) {
        return PhotoDto.builder()
                .photoUrl(photo.getPhotoUrl())
                .build();
    }
}