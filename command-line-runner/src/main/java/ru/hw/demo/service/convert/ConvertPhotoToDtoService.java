package ru.hw.demo.service.convert;

import ru.hw.demo.domain.Photo;
import ru.hw.demo.dto.PhotoDto;

/**
 * @author Artem
 * Интерфейс для конвертации Photo в PhotoDto
 */

public interface ConvertPhotoToDtoService {
    PhotoDto convertToPhotoDto(Photo photo);
}