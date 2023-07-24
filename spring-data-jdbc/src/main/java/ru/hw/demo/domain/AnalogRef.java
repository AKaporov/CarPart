package ru.hw.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("CAR_PART_ANALOGS")
public class AnalogRef {
    @Column(value = "ANALOG_ID")
    Long analogId;

//    @Transient
//    public Long carPartId;


    public AnalogRef() {
    }

//    @PersistenceConstructor
    public AnalogRef(Long analogId) {
        this.analogId = analogId;
    }
}
