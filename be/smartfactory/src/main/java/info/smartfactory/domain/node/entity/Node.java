package info.smartfactory.domain.node.entity;

import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.node.entity.constant.EntranceDirection;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "node")
public abstract class Node extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x_coordinate")
    protected Integer xCoordinate;

    @Column(name = "y_coordinate")
    protected Integer yCoordinate;

    @Enumerated(EnumType.STRING)
    protected EntranceDirection entranceDirection;

    public Node(Integer xCoordinate, Integer yCoordinate, EntranceDirection entranceDirection) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.entranceDirection = entranceDirection;
    }

    public abstract void updateMap(String[][][] map);

}
