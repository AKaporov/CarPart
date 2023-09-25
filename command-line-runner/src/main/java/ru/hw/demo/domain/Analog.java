package ru.hw.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /**
     * Полное описание аналога
     */
    @OneToOne(targetEntity = CarPart.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "car_part_id", nullable = false)
    private CarPart carPart;

    /**
     * продавец
     */
    @Column(name = "vendor", nullable = false)
    private String vendor;
}
