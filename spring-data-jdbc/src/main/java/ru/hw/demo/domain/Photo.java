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
@Table(value = "photos")
public class Photo {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * URL фотографии
     */
//    @Column(name = "url", nullable = false, unique = true)
    @Column(value = "url")
    private String photoUrl;
}
