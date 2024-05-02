package info.smartfactory.domain.bottleneck.entity;

import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bottleneck")
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

    @Column(name = "bottleneck_created_at", nullable = false)
    private LocalDateTime bottleneckCreatedAt;

    public static Bottleneck createBottleneck(
            Integer xCoordinate,
            Integer yCoordinate,
            LocalDateTime bottleneckCreatedAt
    ){
        Bottleneck bottleneck = new Bottleneck();

        bottleneck.xCoordinate = xCoordinate;
        bottleneck.yCoordinate = yCoordinate;
        //bottleneck.mission = mission;
        bottleneck.bottleneckCreatedAt = bottleneckCreatedAt;

        return bottleneck;
    }
}