package ru.hw.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Artem
 * <p>
 * Запчасти автомобиля
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_parts")
@ToString(exclude = {"description", "brand", "model", "engine", "country", "photoList", "analogList"})
public class CarPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Каталожный номер
     */
    @Column(name = "vendor_code", nullable = false, unique = true)
    private String vendorCode;

    /**
     * артикул
     */
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    /**
     * наименование
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * описание
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * стоимость
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * производитель
     */
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    /**
     * рейтинг
     */
    @Column(name = "rating", nullable = false)
    private double rating;

    /**
     * марка
     */
    @ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    /**
     * модель
     */
    @ManyToOne(targetEntity = Model.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    /**
     * двигатель
     */
    @ManyToOne(targetEntity = Engine.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    /**
     * страна производства
     */
    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /**
     * фотографии
     */
    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "car_part_id", nullable = true)
    private List<Photo> photoList;

    /**
     * аналоги
     */
    @OneToMany(targetEntity = Analog.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "car_part_analogs",
            joinColumns = @JoinColumn(name = "car_part_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "analog_id", referencedColumnName = "id"))
    private List<Analog> analogList;
}
