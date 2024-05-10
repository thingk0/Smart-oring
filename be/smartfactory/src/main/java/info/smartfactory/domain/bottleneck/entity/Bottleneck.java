package info.smartfactory.domain.bottleneck.entity;

import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bottleneck_history")
public class Bottleneck extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x_coordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Integer yCoordinate;

    @ManyToOne
    @JoinColumn(name="mission_id")
    private Mission mission;

    @Column(name = "bottleneck_period", nullable = false)
    private Long bottleneckPeriod;

    @Column(name = "bottleneck_created_at", nullable = false)
    private LocalDateTime bottleneckCreatedAt;

    public static Bottleneck createBottleneck(
            Integer xCoordinate,
            Integer yCoordinate,
            Long bottleneckPeriod,
            LocalDateTime bottleneckCreatedAt
    ){
        Bottleneck bottleneck = new Bottleneck();

        bottleneck.xCoordinate = xCoordinate;
        bottleneck.yCoordinate = yCoordinate;
        bottleneck.bottleneckPeriod = bottleneckPeriod;
        bottleneck.bottleneckCreatedAt = bottleneckCreatedAt;

        return bottleneck;
    }
}