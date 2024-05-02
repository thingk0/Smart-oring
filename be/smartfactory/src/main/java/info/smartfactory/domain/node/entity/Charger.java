package info.smartfactory.domain.node.entity;

import info.smartfactory.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("Charger")
@Table(name = "charger")
public class Charger extends Node {
    @Enumerated(EnumType.STRING)
    @Column(name = "entrance_direction")
    private Direction entranceDirection;

    public static Charger createCharger(
            Integer xCoordinate,
            Integer yCoordinate,
            Direction entranceDirection
    ) {
        Charger charger = new Charger();

        charger.xCoordinate = xCoordinate;
        charger.yCoordinate = yCoordinate;
        charger.entranceDirection = entranceDirection;

        return charger;
    }
}