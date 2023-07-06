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
@Table(value = "models")
public class Model {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование
     */
//    @Column(name = "name", nullable = false, unique = true)
    @Column(value = "name")
    private String name;

    /**
     * Год выпуска
     */
//    @Column(name = "year_release", nullable = false, unique = false)
    @Column(value = "year_release")
    private Integer yearRelease;
}
