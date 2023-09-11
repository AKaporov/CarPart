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
 * Марка автомобиля
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "BRANDS")
public class Brand {
    @Id
    private Long id;

    /**
     * Наименование
     */
    @Column(value = "NAME")
    private String name;
}
