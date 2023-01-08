package ru.hw.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PhotoDto {
    /**
     * URL фотографии
     */
    private String photoUrl;
}