package ru.hw.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PhotoDto {
    /**
     * URL фотографии
     */
    private String photoUrl;
}