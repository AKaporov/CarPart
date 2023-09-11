package ru.hw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Artem
 * <p>
 * Фотографии
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "PHOTOS")
public class Photo {

    @Id
    @Column(value = "PHOTO_ID")
    private Long id;

    /**
     * URL фотографии
     */
    @Column(value = "URL")
    private String photoUrl;
}
