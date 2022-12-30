package ru.hw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Artem
 * <p>
 * Аналоги запасных частей
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analogs")
public class Analog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_part_id", nullable = false)
    private Long carPartId;

    /**
     * продавец
     */
    @Column(name = "vendor", nullable = false)
    private String vendor;
}
