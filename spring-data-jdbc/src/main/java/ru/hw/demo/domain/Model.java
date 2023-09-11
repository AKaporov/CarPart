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
 * Модели автомобилей
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "MODELS")
public class Model {

    @Id
    private Long id;

    /**
     * Наименование
     */
    @Column(value = "NAME")
    private String name;

    /**
     * Год выпуска
     */
    @Column(value = "YEAR_RELEASE")
    private Integer yearRelease;
}
