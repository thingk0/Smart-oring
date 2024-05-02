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
@DiscriminatorValue("Destination")
@Table(name = "destination")
public class Destination extends Node{
    public static Destination createDestination(
            Integer xCoordinate,
            Integer yCoordinate,
            Direction entranceDirection
    ){
        Destination destination = new Destination();

        destination.xCoordinate = xCoordinate;
        destination.yCoordinate = yCoordinate;
        destination.entranceDirection = entranceDirection;

        return destination;
    }

}