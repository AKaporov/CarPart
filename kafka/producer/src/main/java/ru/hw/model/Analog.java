package ru.hw.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analog {
    private long id;
    private CarPart carPart;
    private String vendor;
}
