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