package ru.hw.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

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
@ToString(exclude = {"description", "brandRef", "modelRef", "engineRef", "countryRef", "photos", "analogs"})
@EqualsAndHashCode(of = "id")
public class CarPart {

    @Id
    @Column(value = "CAR_PART_ID")
    private Long id;

    /**
     * Каталожный номер
     */
    @Column(value = "VENDOR_CODE")
    private String vendorCode;

    /**
     * артикул
     */
    @Column(value = "SKU")
    private String sku;

    /**
     * наименование
     */
    @Column(value = "NAME")
    private String name;

    /**
     * описание
     */
    @Column(value = "DESCRIPTION")
    private String description;

    /**
     * стоимость
     */
    @Column(value = "PRICE")
    private double price;

    /**
     * производитель
     */
    @Column(value = "MANUFACTURER")
    private String manufacturer;

    /**
     * рейтинг
     */
    @Column(value = "RATING")
    private double rating;

    /**
     * марка
     */
    @Column(value = "BRAND_ID")
    private AggregateReference<Brand, Long> brandRef;
    @Transient // Она будет означать, что поле не будет персистентным, т.е. не будет сохраняться в БД. И соответственно не будет заполняться значением при получении объекта из БД.
    @Column(value = "BRAND_ID")
    private Brand brandQueryDsl;

    /**
     * модель
     */
    @Column(value = "MODEL_ID_FK")
    private AggregateReference<Model, Long> modelRef;
    @Transient // Она будет означать, что поле не будет персистентным, т.е. не будет сохраняться в БД. И соответственно не будет заполняться значением при получении объекта из БД.
    @Column(value = "TEST_KAPOROV")  // Оставлено для примера, что в сочетании с @Transient указанное в @Column поле игнорируется
    private Model modelQueryDsl;

    /**
     * двигатель
     */
    @Column(value = "ENGINE_ID")
    private AggregateReference<Engine, Long> engineRef;
    @Transient // Она будет означать, что поле не будет персистентным, т.е. не будет сохраняться в БД. И соответственно не будет заполняться значением при получении объекта из БД.
    private Engine engineQueryDsl;  // Я не придумал, как "подружить" QueryDsl с AggregateReference, кроме этого варианта.

    /**
     * Страна производства
     */
    @Column(value = "COUNTRY_ID")
    private AggregateReference<Country, Long> countryRef;
    @Transient // Она будет означать, что поле не будет персистентным, т.е. не будет сохраняться в БД. И соответственно не будет заполняться значением при получении объекта из БД.
    private Country countryQueryDsl;  // Я не придумал, как "подружить" QueryDsl с AggregateReference, кроме этого варианта.

    /**
     * фотографии
     */
    @MappedCollection(idColumn = "CAR_PART_ID")
    private Set<Photo> photos;  // Я не понял почему, если сделать List<Photo>, то НЕ работает(Column "PHOTOS.CAR_PARTS_KEY" not found;)! Система ожидает поле "CAR_PARTS_KEY" в таблице "PHOTOS"."CAR_PARTS_KEY"

    /**
     * аналоги
     */
    @MappedCollection(idColumn = "CAR_PART_ID")
    private Set<AnalogRef> analogs;  // Я не понял почему, если сделать List<AnalogRef> или Collection<AnalogRef>, то НЕ работает (Column "CAR_PART_ANALOGS.CAR_PARTS_KEY" not found;)!  Система ожидает поле "CAR_PARTS_KEY" в таблице "CAR_PART_ANALOGS"."CAR_PARTS_KEY"


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

//    public CarPart(String vendorCode,
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
//        this.id = null;
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
