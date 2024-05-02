package info.smartfactory.domain.node.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("Storage")
@Table(name = "storage")
public class Storage extends Node{
    @Enumerated(EnumType.STRING)
    @Column(name = "entrance_direction")
    private Direction entranceDirection;

    public static Storage createStorage(
            Integer xCoordinate,
            Integer yCoordinate,
            Direction entranceDirection
    ){
        Storage storage = new Storage();

        storage.xCoordinate = xCoordinate;
        storage.yCoordinate = yCoordinate;
        storage.entranceDirection = entranceDirection;

        return storage;
    }

}