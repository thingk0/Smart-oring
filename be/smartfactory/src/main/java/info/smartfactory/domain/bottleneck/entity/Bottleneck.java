package info.smartfactory.domain.bottleneck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bottleneck")
public class Bottleneck {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "x_coordinate")
    private Integer xCoordinate;

    @Column(name = "y_coordinate")
    private Integer yCoordinate;

    @Column(name = "bottleneck_created_at")
    private Instant bottleneckCreatedAt;

}