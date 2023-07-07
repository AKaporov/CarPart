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
 * Аналоги запасных частей
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "ANALOGS")
public class Analog {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Полное описание аналога
     */
    @Column(value = "CAR_PART_ID")
    private CarPart carPart;

    /**
     * продавец
     */
    @Column(value = "VENDOR")//, nullable = false)
    private String vendor;
}
