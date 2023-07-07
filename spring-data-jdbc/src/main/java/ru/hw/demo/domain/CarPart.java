package ru.hw.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
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
@Table(value = "CAR_PARTS")
@ToString(exclude = {"description", "brand", "model", "engine", "country", "photoList", "analogList"})
public class CarPart {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Каталожный номер
     */
    @Column(value = "VENDOR_CODE")//, nullable = false, unique = true)
    private String vendorCode;

    /**
     * артикул
     */
    @Column(value = "SKU")//, nullable = false, unique = true)
    private String sku;

    /**
     * наименование
     */
    @Column(value = "NAME")//, nullable = false)
    private String name;

    /**
     * описание
     */
    @Column(value = "DESCRIPTION")//, nullable = false)
    private String description;

    /**
     * стоимость
     */
    @Column(value = "PRICE")//, nullable = false)
    private double price;

    /**
     * производитель
     */
    @Column(value = "MANUFACTURER")//, nullable = false)
    private String manufacturer;

    /**
     * рейтинг
     */
    @Column(value = "RATING") //, nullable = false)
    private double rating;

    /**
     * марка
     */
//    @ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand_id", nullable = false)
     @Column(value = "BRAND_ID")
    private Brand brand;

    /**
     * модель
     */
//    @ManyToOne(targetEntity = Model.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "model_id", nullable = false)
    @Column(value = "MODEL_ID")
    private Model model;

    /**
     * двигатель
     */
//    @ManyToOne(targetEntity = Engine.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "engine_id", nullable = false)
    @Column(value = "ENGINE_ID")
    private Engine engine;

    /**
     * Страна производства
     */
//    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id", nullable = false)
    @Column(value = "COUNTRY_ID")
    private Country country;

    /**
     * фотографии
     */
//    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "car_part_id", nullable = true)
//    @Column(value = "CAR_PART_ID")
    @MappedCollection(idColumn = "CAR_PART_ID_PHOTO")
    private List<Photo> photoList;

    /**
     * аналоги
     */
//    @OneToMany(targetEntity = Analog.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "car_part_analogs",
//            joinColumns = @JoinColumn(name = "car_part_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "analog_id", referencedColumnName = "id"))
//    @Column(value = "CAR_PART_ID")
    @MappedCollection(idColumn = "CAR_PART_ID_ANALOG")
    private List<Analog> analogList;
}
