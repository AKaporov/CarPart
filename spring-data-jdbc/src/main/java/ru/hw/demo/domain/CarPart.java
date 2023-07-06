package ru.hw.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
@Table(value = "car_parts")
@ToString(exclude = {"description", "brand", "model", "engine", "country", "photoList", "analogList"})
public class CarPart {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * каталожный номер
     */
    @Column(value = "vendor_code")//, nullable = false, unique = true)
    private String vendorCode;

    /**
     * артикул
     */
    @Column(value = "sku")//, nullable = false, unique = true)
    private String sku;

    /**
     * наименование
     */
    @Column(value = "name")//, nullable = false)
    private String name;

    /**
     * описание
     */
    @Column(value = "description")//, nullable = false)
    private String description;

    /**
     * стоимость
     */
    @Column(value = "price")//, nullable = false)
    private double price;

    /**
     * производитель
     */
    @Column(value = "manufacturer")//, nullable = false)
    private String manufacturer;

    /**
     * рейтинг
     */
    @Column(value = "rating") //, nullable = false)
    private double rating;

    /**
     * марка
     */
//    @ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand_id", nullable = false)
     @Column(value = "brand_id")
    private Brand brand;

    /**
     * модель
     */
//    @ManyToOne(targetEntity = Model.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "model_id", nullable = false)
    @Column(value = "model_id")
    private Model model;

    /**
     * двигатель
     */
//    @ManyToOne(targetEntity = Engine.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "engine_id", nullable = false)
    @Column(value = "engine_id")
    private Engine engine;

    /**
     * страна производства
     */
//    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id", nullable = false)
    @Column(value = "country_id")
    private Country country;

    /**
     * фотографии
     */
//    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "car_part_id", nullable = true)
    @Column(value = "car_part_id")
    private List<Photo> photoList;

    /**
     * аналоги
     */
//    @OneToMany(targetEntity = Analog.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "car_part_analogs",
//            joinColumns = @JoinColumn(name = "car_part_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "analog_id", referencedColumnName = "id"))
    @Column(value = "car_part_analogs")
    private List<Analog> analogList;
}
