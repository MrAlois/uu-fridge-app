package cz.asen.unicorn.fridge.domain.enums;

import lombok.Getter;

@Getter
public enum DistanceType {
    ALL     (0.0),
    KM_2    (2000.0),
    KM_5    (5000.0),
    KM_10   (10000.0),
    KM_20   (20000.0);

    private final double valueInMeter;

    DistanceType(double valueInMeter) {
        this.valueInMeter = valueInMeter;
    }
}
