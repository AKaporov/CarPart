package ru.hw.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Artem
 * <p>
 * Модели автомобилей
 */

@Builder
@Data
@RequiredArgsConstructor
@Table(value = "MODELS")
public class Model {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /**
     * Наименование
     */
//    @Column(name = "name", nullable = false, unique = true)
    @Column(value = "NAME")
    private final String name;

    /**
     * Год выпуска
     */
//    @Column(name = "year_release", nullable = false, unique = false)
    @Column(value = "YEAR_RELEASE")
    private final Integer yearRelease;
}
