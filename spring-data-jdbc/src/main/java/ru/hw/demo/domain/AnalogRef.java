package ru.hw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("CAR_PART_ANALOGS")
public class AnalogRef {
    @Column(value = "ANALOG_ID")
    private Long analogId;
}
