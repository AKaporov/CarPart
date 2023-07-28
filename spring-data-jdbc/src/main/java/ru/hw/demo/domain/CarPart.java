package ru.hw.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem
 * <p>
 * Запчасти автомобиля
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(value = "CAR_PARTS")
@ToString(exclude = {"description", "brand", "model", "engine", "country", "photoList", "analogList"})
public class CarPart {

    @Id
    @Column(value = "CAR_PART_ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(value = "BRAND_ID")
    private AggregateReference<Brand, Long> brandRef;

    /**
     * модель
     */
//    @ManyToOne(targetEntity = Model.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "model_id", nullable = false)
    @Column(value = "MODEL_ID_FK")
    private AggregateReference<Model, Long> modelRef;

    /**
     * двигатель
     */
//    @ManyToOne(targetEntity = Engine.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "engine_id", nullable = false)
    @Column(value = "ENGINE_ID")
    private AggregateReference<Engine, Long> engineRef;

    /**
     * Страна производства
     */
//    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "country_id", nullable = false)
    @Column(value = "COUNTRY_ID")
    private AggregateReference<Country, Long> countryRef;

    /**
     * фотографии
     */
//    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "car_part_id", nullable = true)
//    @Column(value = "CAR_PART_ID")
    @MappedCollection(idColumn = "CAR_PART_ID")
    private Set<Photo> photos = new HashSet<>();

    /**
     * аналоги
     */
//    @OneToMany(targetEntity = Analog.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "car_part_analogs",
//            joinColumns = @JoinColumn(name = "car_part_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "analog_id", referencedColumnName = "id"))
//    @Column(value = "CAR_PART_ID")
//    private final List<Analog> analogList;
    @MappedCollection(idColumn = "CAR_PART_ID")
    private Set<AnalogRef> analogs = new HashSet<>();


    public void addPhoto(Photo photo) {
        Assert.notNull(photo, "Photo must not be null");
        Assert.notNull(photo.getId(), "Photo id, must not be null");

        this.photos.add(photo);
    }

    public void addAnalog(AnalogRef analogRef) {
        Assert.notNull(analogRef, "AnalogRef must not be null");
        Assert.notNull(analogRef.getAnalogId(), "AnalogRef analog_id, must not be null");

        this.analogs.add(analogRef);
//        this.analogs.add(craeteAnalogRef(analog));
    }

//    private AnalogRef craeteAnalogRef(Analog analog) {
//        Assert.notNull(analog, "Analog must not be null");
//        Assert.notNull(analog.getId(), "Analog id, must not be null");
//
//        AnalogRef analogRef = new AnalogRef();
//        analogRef.setAnalogId(analog.getId());
//        return analogRef;
//    }

//    public CarPart(Long id,
//            String vendorCode,
//                   String sku,
//                   String name,
//                   String description,
//                   double price,
//                   String manufacturer,
//                   double rating,
//                   AggregateReference<Brand, Long> brandRef,
//                   AggregateReference<Model, Long> modelRef,
//                   AggregateReference<Engine, Long> engineRef,
//                   AggregateReference<Country, Long> countryRef,
//                   Collection<Photo> photos,
//                   Collection<AnalogRef> analogs
//    ) {
//        this.id = id;
//        this.vendorCode = vendorCode;
//        this.sku = sku;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.manufacturer = manufacturer;
//        this.rating = rating;
//        this.brandRef = brandRef;
//        this.modelRef = modelRef;
//        this.engineRef = engineRef;
//        this.countryRef = countryRef;
//        photos.forEach(this::addPhoto);
//        analogs.forEach(this::addAnalog);
//    }

//    @PersistenceConstructor
//    public CarPart(Long id,
//                   String vendorCode,
//                   String sku,
//                   String name,
//                   String description,
//                   double price,
//                   String manufacturer,
//                   double rating,
//                   AggregateReference<Brand, Long> brandRef,
//                   AggregateReference<Model, Long> modelRef,
//                   AggregateReference<Engine, Long> engineRef,
//                   AggregateReference<Country, Long> countryRef,
//                   Collection<Photo> photos,
//                   Collection<AnalogRef> analogs) {
//        this.id = id;
//        this.vendorCode = vendorCode;
//        this.sku = sku;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.manufacturer = manufacturer;
//        this.rating = rating;
//        this.brandRef = brandRef;
//        this.modelRef = modelRef;
//        this.engineRef = engineRef;
//        this.countryRef = countryRef;
//        photos.forEach(this::addPhoto);
//        analogs.forEach(this::addAnalog);
//    }

}
